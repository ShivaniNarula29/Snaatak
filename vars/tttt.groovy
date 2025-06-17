def call(Map config = [:]) {
    pipeline {
        agent any

        environment {
            ODC_DB = '/var/lib/jenkins/odc-db'
            SLACK_CHANNEL = config.slackChannel ?: 'golang-notification'
            SLACK_CREDENTIAL_ID = config.slackCredentialId ?: 'downtime-crew'
            EMAIL_TO = config.emailRecipient ?: 'shivani.narula.snaatak@mygurukulam.co'
            EMPLOYEE_REPO = config.repoUrl ?: 'https://github.com/snaatak-Downtime-Crew/employee-api.git'
        }

        stages {
            stage('Clean Workspace') {
                steps {
                    cleanWs()
                }
            }

            stage('Clone Repository') {
                steps {
                    dir('employee-api') {
                        git credentialsId: config.gitCredId ?: 'shivani-git-cred', url: "${env.EMPLOYEE_REPO}", branch: config.branch ?: 'main'
                    }
                }
            }

            stage('Run OWASP Dependency Check') {
                steps {
                    sh """
                        mkdir -p odc-employee-report
                        /opt/dependency-check/bin/dependency-check.sh \\
                            --data ${ODC_DB} \\
                            --project "Employee API" \\
                            --scan employee-api \\
                            --format HTML \\
                            --out odc-employee-report
                    """
                }
            }

            stage('Publish Report') {
                steps {
                    dependencyCheckPublisher pattern: 'odc-employee-report/dependency-check-report.html'
                    archiveArtifacts artifacts: 'odc-employee-report/dependency-check-report.html', allowEmptyArchive: true
                }
            }
        }

        post {
            success {
                slackSend(
                    channel: env.SLACK_CHANNEL,
                    color: 'good',
                    message: """✅ *Build #${env.BUILD_NUMBER} succeeded*
• Job: ${env.JOB_NAME}
• URL: ${env.BUILD_URL}""",
                    tokenCredentialId: env.SLACK_CREDENTIAL_ID
                )
                mail(
                    to: "${env.EMAIL_TO}",
                    subject: "Build #${env.BUILD_NUMBER} - SUCCESS",
                    body: """\
Your Jenkins build succeeded.

• Job: ${env.JOB_NAME}
• Build: ${env.BUILD_NUMBER}
• [Employee Report](${env.BUILD_URL}artifact/odc-employee-report/dependency-check-report.html)
"""
                )
            }

            failure {
                slackSend(
                    channel: env.SLACK_CHANNEL,
                    color: 'danger',
                    message: """❌ *Build #${env.BUILD_NUMBER} failed*
• Job: ${env.JOB_NAME}
• URL: ${env.BUILD_URL}""",
                    tokenCredentialId: env.SLACK_CREDENTIAL_ID
                )
                mail(
                    to: "${env.EMAIL_TO}",
                    subject: "Build #${env.BUILD_NUMBER} - FAILURE",
                    body: "The Jenkins build failed. Please check logs."
                )
            }
        }
    }
}
