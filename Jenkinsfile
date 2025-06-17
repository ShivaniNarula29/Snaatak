@Library('shared-library') _

node {
    def config = [
        reportDir     : 'target',
        reportHtml    : 'dependency-check-report.html',
        cacheDir      : '/var/lib/jenkins/odc-db',
        repoUrl       : 'https://github.com/snaatak-Downtime-Crew/frontend-api.git',
        branch        : 'main',
        credentialsId : 'downtime_github',
        projectName   : 'frontend-api'
    ]

    def BUILD_TRIGGER = ''
    def reportUrl = ''
    def failedStage = ''
    def EMAIL_TO = 'shivani.narula.snaatak@gmail.com'
    def SLACK_CHANNEL = 'golang-notification'
    def SLACK_CREDENTIAL_ID = 'downtime-crew'

    try {
        go(config)
        BUILD_TRIGGER = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'
        reportUrl = "${env.BUILD_URL}artifact/${config.reportDir}/${config.reportHtml}"
    } catch (err) {
        failedStage = currentBuild.currentStage ?: 'Unknown'
        throw err
    } finally {
        def subject, emailMessage, slackMessage

        if (currentBuild.result == null || currentBuild.result == 'SUCCESS') {
            subject = "✅ Dependency Check SUCCESS: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
            emailMessage = """
✅ Dependency Check Completed Successfully

🔧 Job: ${env.JOB_NAME}
👤 Triggered by: ${BUILD_TRIGGER}
🔗 Build URL: ${env.BUILD_URL}

📄 Report:
- ${reportUrl}
"""
            slackMessage = """
:white_check_mark: *Dependency Check SUCCESS - Build #${env.BUILD_NUMBER}*

*🔧 Job:* `${env.JOB_NAME}`
*👤 Triggered by:* ${BUILD_TRIGGER}
*🔗 Job URL:* <${env.BUILD_URL}|Open Job>

📄 *Report:*
- <${reportUrl}|Dependency Check Report>
"""
        } else {
            subject = "❌ Dependency Check FAILURE: ${env.JOB_NAME} #${env.BUILD_NUMBER}"
            emailMessage = """
❌ Dependency Check Failed

🔧 Job: ${env.JOB_NAME}
👤 Triggered by: ${BUILD_TRIGGER}
⚠️ Priority: P1
💥 Failed at: ${failedStage}
🔗 Logs: ${env.BUILD_URL}
"""
            slackMessage = """
:x: *Dependency Check FAILED - Build #${env.BUILD_NUMBER}*

*🔧 Job:* `${env.JOB_NAME}`
*👤 Triggered by:* ${BUILD_TRIGGER}
*⚠️ Priority:* *P1*
*💥 Failed at:* *${failedStage}*
*🔗 Job URL:* <${env.BUILD_URL}|View Logs>
"""
        }

        echo emailMessage

        mail(
            to: EMAIL_TO,
            subject: subject,
            body: emailMessage
        )

        slackSend(
            channel: SLACK_CHANNEL,
            color: (currentBuild.result == null || currentBuild.result == 'SUCCESS') ? 'good' : 'danger',
            message: slackMessage,
            tokenCredentialId: SLACK_CREDENTIAL_ID
        )

        cleanWs()
    }
}
