def call(Map config = [:]) {
    def UNIT_TEST_SCOPE = config.get('UNIT_TEST_SCOPE', 'ALL')
    def employeeRepo = config.get('employeeRepo')
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
            stage('Set JDK and PATH') {
                buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'
                currentStage = 'Set JDK and PATH'
                env.JAVA_HOME = tool name: 'JDK_17'
                env.PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
            }

            stage('Clean Workspace') {
                currentStage = 'Clean Workspace'
                cleanWs()
            }

            stage('Checkout Employee-api Repo') {
                currentStage = 'Checkout Employee-api Repo'
                dir('employee-api') {
                    checkout([$class: 'GitSCM', branches: [[name: 'main']],
                        userRemoteConfigs: [[url: employeeRepo, credentialsId: 'shivani-git-cred']]])
                }
            }

            stage('Dependency Check') {
                currentStage = 'Dependency Check'
                dir('employee-api') {
                    try {
                        dependencyCheck()
                    } catch (e) {
                        failureReason = "Dependency Check failed: ${e.getMessage()}"
                        failedStage = currentStage
                        echo "❌ ${failureReason}"
                        error(failureReason)
                    }
                }
            }

            currentBuild.result = currentBuild.result ?: 'SUCCESS'

        } catch (Exception e) {
            if (!failedStage) failedStage = currentStage ?: 'Unknown Stage'
            if (!failureReason) failureReason = e.message
            currentBuild.result = 'FAILURE'
        } finally {
            stage('Post Actions') {
                echo "📄 Dependency scanning pipeline finished. Sending notifications."
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
