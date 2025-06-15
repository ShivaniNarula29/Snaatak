@Library('shared-library') _

def customTitle = 'Generic Notification Status:'

pipeline {
    agent any

    environment {
        SLACK_CHANNEL = 'notificationn-channel'
        SLACK_CREDENTIAL_ID = 'downtime-crew'
        EMAIL_RECIPIENTS = 'shivani.narula.snaatak@mygurukulam.co'
        PRIORITY = 'P1'
    }

    stages {
        stage('Run Stages') {
            steps {
                script {
                    def state = [:]
                    state = genericnotificaiton.initializeBuild(state)

                    // Wrap each stage in runStage
                    state = genericnotificaiton.runStage(state, 'Initialize') {
                        echo '🔧 Initializing...'
                        // Your actual logic here
                    }

                    state = genericnotificaiton.runStage(state, 'Cred Scanning') {
                        echo '🔍 Performing credential scan...'
                        // Your actual logic here
                    }

                    state = genericnotificaiton.runStage(state, 'License Scanning') {
                        echo '📜 Performing license scan...'
                        // Your actual logic here
                    }

                    state = genericnotificaiton.runStage(state, 'AMI Build') {
                        echo '📦 Building AMI...'
                        // Your actual logic here
                    }

                    state = genericnotificaiton.runStage(state, 'Commit Sign-off') {
                        echo '✅ Committing sign-off...'
                        // Your actual logic here
                    }

                    // Save state to env for post block access
                    env._PIPELINE_STATE = groovy.json.JsonOutput.toJson(state)
                }
            }
        }
    }

    post {
        success {
            script {
                def state = readJSON text: env._PIPELINE_STATE
                genericnotificaiton.notifyBuildStatus(state, 'SUCCESS', "${customTitle} SUCCESS")
            }
        }

        failure {
            script {
                def state = readJSON text: env._PIPELINE_STATE
                genericnotificaiton.notifyBuildStatus(state, 'FAILURE', "${customTitle} FAILURE")
            }
        }

        always {
            echo "📄 Pipeline execution completed."
        }
    }
}
