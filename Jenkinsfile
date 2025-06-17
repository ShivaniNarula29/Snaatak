@Library('shared-library') _

// declare these globally for post block use
def failedStage = ''
def failureReason = ''

pipeline {
    agent any

    environment {
        BUILD_SUMMARY = ''
        SLACK_CHANNEL = 'notificationn-channel'
        SLACK_CREDENTIAL_ID = 'downtime-crew'
        EMAIL_RECIPIENTS = 'shivani.narula.snaatak@mygurukulam.co'
        PRIORITY = 'P1'
    }

    options {
        timestamps()
    }

    stages {
        stage('Initialize') {
            steps {
                script {
                    genericnotificaiton.initializeBuild()
                }
            }
        }

        stage('Cred Scanning') {
            steps {
                script {
                    genericnotificaiton.runStage('Cred Scanning') {
                        sh 'echo "No credentials found"'
                    }
                }
            }
        }

        stage('License Scanning') {
            steps {
                script {
                    genericnotificaiton.runStage('License Scanning') {
                        sh 'echo "All licenses compliant"'
                    }
                }
            }
        }

        stage('AMI Build') {
            steps {
                script {
                    genericnotificaiton.runStage('AMI Build') {
                        sh 'echo "AMI built successfully"'
                    }
                }
            }
        }

        stage('Commit Sign-off') {
            steps {
                script {
                    genericnotificaiton.runStage('Commit Sign-off') {
                        echo "Sign-off check passed"
                    }
                }
            }
        }
    }

    post {
        success {
            script {
                genericnotificaiton.notifyBuildStatus('SUCCESS')
            }
        }
        failure {
            script {
                if (!failedStage) {
                    failedStage = currentStage ?: 'Unknown Stage'
                    failureReason = failureReason ?: 'Unknown failure reason'
                }
                genericnotificaiton.notifyBuildStatus('FAILURE')
            }
        }
        always {
            echo "📄 Pipeline execution completed."
        }
    }
}
