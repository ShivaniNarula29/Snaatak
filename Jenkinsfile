/* @Library('shared-library') _

golangdependencyscanning(
    gitUrl: 'https://github.com/snaatak-Downtime-Crew/employee-api.git',
    gitCredId: 'shivani-git-cred',
    slackChannel: 'notificationn-channel',
    slackTokenCredentialId: 'downtime-crew',
    emailRecipients: 'shivani.narula.snaatak@mygurukulam.co'
)
*/


@Library('shared-library') _

def customTitle = 'Generic Notification Status:'

pipeline {
    agent any

    environment {
        SLACK_CHANNEL = 'notificationn-channel'
        SLACK_CREDENTIAL_ID = 'downtime-crew'
        EMAIL_RECIPIENTS = 'shivani.narula.snaatak@mygurukulam.co'
        PRIORITY  = 'P1'
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
                    genericnotificaiton.credScanning()
                }
            }
        }

        stage('License Scanning') {
            steps {
                script {
                    genericnotificaiton.licenseScanning()
                }
            }
        }

        stage('AMI Build') {
            steps {
                script {
                    genericnotificaiton.buildAMI()
                }
            }
        }

        stage('Commit Sign-off') {
            steps {
                script {
                    genericnotificaiton.commitSignoff()
                }
            }
        }
    }

    post {
        success {
            script {
                genericnotify.notifyBuildStatus(
                    env.SLACK_CHANNEL,
                    env.SLACK_CREDENTIAL_ID,
                    env.EMAIL_RECIPIENTS,
                    env.PRIORITY,
                    'SUCCESS',
                    "${customTitle} SUCCESS"
                )
            }
        }

        failure {
            script {
                def failedStage = genericnotify.getFailedStage(currentBuild)
                genericnotify.notifyBuildStatus(
                    env.SLACK_CHANNEL,
                    env.SLACK_CREDENTIAL_ID,
                    env.EMAIL_RECIPIENTS,
                    env.PRIORITY,
                    'FAILURE',
                    "${customTitle} FAILURE",
                    failedStage
                )
            }
        }

        always {
            echo "📄 Pipeline execution completed."
        }
    }
}
