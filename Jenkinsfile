@Library('shared-library') _

def state = [:] // Initial empty map

pipeline {
    agent any

    environment {
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
                    state = genericnotificaiton.initializeBuild(state)
                }
            }
        }

        stage('Cred Scanning') {
            steps {
                script {
                    state = genericnotificaiton.runStage(state, 'Cred Scanning') {
                        sh 'echo "No credentials found"'
                    }
                }
            }
        }

        stage('License Scanning') {
            steps {
                script {
                    state = genericnotificaiton.runStage(state, 'License Scanning') {
                        sh 'echo "All licenses compliant"'
                    }
                }
            }
        }

        stage('AMI Build') {
            steps {
                script {
                    state = genericnotificaiton.runStage(state, 'AMI Build') {
                        sh 'echo "AMI built successfully"'
                    }
                }
            }
        }

        stage('Commit Sign-off') {
            steps {
                script {
                    state = genericnotificaiton.runStage(state, 'Commit Sign-off') {
                        echo "Sign-off check passed"
                    }
                }
            }
        }
    }

    post {
        success {
            script {
                genericnotificaiton.notifyBuildStatus(state, 'SUCCESS')
            }
        }
        failure {
            script {
                genericnotificaiton.notifyBuildStatus(state, 'FAILURE')
            }
        }
        always {
            echo "📄 Pipeline execution completed."
        }
    }
}
