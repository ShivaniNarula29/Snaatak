def notifyBuildStatus(
    String slackChannel,
    String slackCredentialsId,
    String emailRecipients,
    String priority,
    String status,
    String customTitle,
    String failedStage = ""
) {
    def isSuccess = (status == 'SUCCESS')
    def color = isSuccess ? 'good' : 'danger'
    def icon = isSuccess ? '✅' : '❌'
    def now = new Date().format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('Asia/Kolkata'))
    def triggeredBy = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'
    def titlePrefix = (customTitle?.trim()) ? customTitle : "Build #${env.BUILD_NUMBER}"
    def title = "${titlePrefix} - ${status}"

    def failureReason = ""
    if (status == "FAILURE") {
        try {
            def logLines = currentBuild.rawBuild.getLog(100)
            def errorLine = logLines.find { it.contains("Exception") || it.contains("ERROR") || it.contains("FAILURE") }
            failureReason = errorLine ? "<strong>Reason for Failure:</strong> ${errorLine.trim()}" : "<strong>Reason for Failure:</strong> Not found in last 100 log lines."
        } catch (Exception e) {
            failureReason = "<strong>Reason for Failure:</strong> Could not retrieve log lines. Error: ${e.getMessage()}"
        }
    }

    def getFailedStage(currentBuild) {
        try {
            return currentBuild.rawBuild.getExecution().getCurrentHeads()[0].getDisplayName()
        } catch (Exception e) {
            return "Unknown Stage"
        }
    }

    def slackMsg = """\
*${title}*

*Job:* `${env.JOB_NAME}`
*Build Number:* #${env.BUILD_NUMBER}
*Triggered by:* ${triggeredBy}
*Time (IST):* ${now}
*Priority:* *${priority}*
${!isSuccess ? "*💥 Failed at:* *${failedStage}*\n*📛 ${failureReason.replaceAll('<[^>]*>', '')}*\n" : ""}
🔗 *Build URL:* <${env.BUILD_URL}|View Logs>
"""

    def emailSubject = title
    def emailBody = """
<html>
    <body>
        <h2>${icon} Jenkins Build Notification: ${status}</h2>
        <ul>
            <li><strong>Job:</strong> ${env.JOB_NAME}</li>
            <li><strong>Build Number:</strong> #${env.BUILD_NUMBER}</li>
            <li><strong>Status:</strong> <span style="color:${isSuccess ? 'green' : 'red'}">${status}</span></li>
            <li><strong>Triggered By:</strong> ${triggeredBy}</li>
            <li><strong>Time (IST):</strong> ${now}</li>
            <li><strong>Priority:</strong> ${priority}</li>
            ${!isSuccess ? "<li><strong>Failed Stage:</strong> ${failedStage}</li><li>${failureReason}</li>" : ""}
            <li><strong>Build URL:</strong> <a href='${env.BUILD_URL}'>Click here</a></li>
        </ul>
    </body>
</html>
"""

    echo "Sending Slack Message: \n${slackMsg}"

    if (emailRecipients?.trim()) {
        mail(
            to: emailRecipients,
            subject: emailSubject,
            body: emailBody,
            mimeType: 'text/html'
        )
    } else {
        echo "No email recipients specified, skipping email notification."
    }

    if (slackChannel?.trim() && slackCredentialsId?.trim()) {
        slackSend(
            channel: slackChannel,
            color: color,
            message: slackMsg,
            tokenCredentialId: slackCredentialsId
        )
    } else {
        echo "Missing Slack channel or credential ID, skipping Slack notification."
    }
}
