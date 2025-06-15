@Library('shared-library') _

pipeline {
    agent any

    stages {
        stage('Execute Pipeline') {
            steps {
                script {
                    genericnotificaiton(
                        slackChannel: 'notificationn-channel',
                        slackCredentialId: 'downtime-crew',
                        emailRecipients: 'shivani.narula.snaatak@mygurukulam.co',
                        priority: 'P1'
                    )
                }
            }
        }
    }
}
