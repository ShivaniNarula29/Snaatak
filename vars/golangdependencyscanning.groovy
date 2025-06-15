def call(Map config = [:]) {
    def UNIT_TEST_SCOPE = config.get('UNIT_TEST_SCOPE', 'ALL')
    def attendanceRepo = config.get('attendanceRepo')
    def notificationRepo = config.get('notificationRepo')
    def slackChannel = config.get('slackChannel')
    def slackCredentialId = config.get('slackCredentialId')
    def emailTo = config.get('emailTo')
    def priority = config.get('priority', 'P1')

    def buildTrigger = ''
    def failureReason = ''
    def failedStage = ''
    def currentStage = ''

    timestamps {
        try {
            stage('Initialize') {
                currentStage = 'Initialize'
                buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'
            }

            stage('Clean Workspace') {
                currentStage = 'Clean Workspace'
                cleanWs()
            }

            stage('Install Packages') {
                currentStage = 'Install Packages'
                echo 'Checking and installing required packages if missing...'
                sh '''
                    set -e
                    check_package() {
                        dpkg -s "$1" >/dev/null 2>&1
                    }

                    MISSING=0
                    for pkg in python3 python3-pip python3-venv; do
                        if check_package "$pkg"; then
                            echo "$pkg is already installed."
                        else
                            echo "$pkg is NOT installed."
                            MISSING=1
                        fi
                    done

                    if [ "$MISSING" -eq 1 ]; then
                        echo "Installing missing packages..."
                        sudo apt-get update
                        sudo apt-get install -y python3 python3-pip python3-venv
                    else
                        echo "All required packages are already installed."
                    fi
                '''
            }

            stage('Checkout Repositories') {
                currentStage = 'Checkout Repositories'
                dir('attendance-api') {
                    checkout([$class: 'GitSCM', branches: [[name: 'main']],
                              userRemoteConfigs: [[url: attendanceRepo, credentialsId: 'shivani-git-cred']]])
                }
                dir('notification-worker') {
                    checkout([$class: 'GitSCM', branches: [[name: 'main']],
                              userRemoteConfigs: [[url: notificationRepo, credentialsId: 'shivani-git-cred']]])
                }
            }

            stage('Run Unit Tests') {
                currentStage = 'Run Unit Tests'
                def allTests = [
                    [name: 'Attendance API', key: 'ATTENDANCE', path: 'attendance-api', venv: 'venv1', report: 'unit_test_attendance.txt'],
                    [name: 'Notification Worker', key: 'NOTIFICATION', path: 'notification-worker', venv: 'venv2', report: 'unit_test_notification.txt']
                ]

                def selectedTests = allTests.findAll { test ->
                    UNIT_TEST_SCOPE == 'ALL' || UNIT_TEST_SCOPE == test.key
                }

                for (test in selectedTests) {
                    currentStage = "${test.name} Unit Tests"
                    dir(test.path) {
                        try {
                            echo "Running ${test.name} unit tests..."
                            sh """
                                python3 -m venv ${test.venv}
                                . ${test.venv}/bin/activate
                                pip install -r requirements.txt || true
                                pip install pytest
                                pytest > ${test.report} || true
                                deactivate
                            """
                            def result = sh(script: "grep -q 'FAILED' ${test.report}", returnStatus: true)
                            if (result == 0) {
                                echo "⚠️ Some tests failed in ${test.name}."
                                currentBuild.result = currentBuild.result != 'FAILURE' ? 'UNSTABLE' : currentBuild.result
                                failureReason = "Some unit tests failed in ${test.name}."
                                failedStage = currentStage
                            }
                            archiveArtifacts artifacts: test.report, allowEmptyArchive: true
                        } catch (e) {
                            echo "❌ Exception in ${test.name} tests: ${e.getMessage()}"
                            failureReason = "${test.name} tests failed: ${e.getMessage()}"
                            failedStage = currentStage
                            currentBuild.result = 'FAILURE'
                            throw e
                        }
                    }
                }
            }

            currentBuild.result = currentBuild.result ?: 'SUCCESS'

        } catch (e) {
            echo "🔥 Pipeline failed at stage: ${currentStage}"
            echo "Reason: ${e.getMessage()}"
            if (!failedStage) failedStage = currentStage ?: 'Unknown Stage'
            if (!failureReason) failureReason = e.getMessage()
            currentBuild.result = 'FAILURE'
        } finally {
            stage('Post Actions') {
                echo "📦 Build completed. Sending notifications..."
                notifyBuildStatus(
                    status: currentBuild.result,
                    scope: UNIT_TEST_SCOPE,
                    buildTrigger: buildTrigger,
                    failureReason: failureReason,
                    failedStage: failedStage,
                    slackChannel: slackChannel,
                    slackCredId: slackCredentialId,
                    emailTo: emailTo,
                    priority: priority
                )
            }
        }
    }
}
