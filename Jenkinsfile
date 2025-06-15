@Library('shared-library') _

pipeline {
    agent any

    options {
        timestamps()
    }

    environment {
        SLACK_CHANNEL = 'notificationn-channel'
        SLACK_CREDENTIAL_ID = 'downtime-crew'
        EMAIL_RECIPIENTS = 'shivani.narula.snaatak@mygurukulam.co'
        PRIORITY = 'P1'
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
        always {
            script {
                genericnotificaiton.sendNotifications(
                    env.SLACK_CHANNEL,
                    env.SLACK_CREDENTIAL_ID,
                    env.EMAIL_RECIPIENTS
                )
            }
        }
    }
}
