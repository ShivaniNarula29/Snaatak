def initializeBuild(state) {
    state.passedStages = 0
    state.failedStage = ''
    state.failureReason = ''
    state.currentStage = ''
    state.buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'
    currentBuild.description = ''
    return state
}

def runStage(state, String stageName, Closure body) {
    state.currentStage = stageName
    if (!state.containsKey('totalStages')) {
        state.totalStages = 0
    }
    state.totalStages++

    try {
        echo "▶️ Running ${stageName}..."
        body()
        currentBuild.description += "\n✅ ${stageName}: Success"
        state.passedStages++
    } catch (e) {
        state.failureReason = "${stageName} failed: ${e.message}"
        state.failedStage = stageName
        currentBuild.description += "\n❌ ${stageName}: ${e.message}"
        error(state.failureReason)
    }

    return state
}

def notifyBuildStatus(state, String status, String customTitle = null) {
    def isSuccess = (status == 'SUCCESS')
    def color = isSuccess ? 'good' : 'danger'
    def icon = isSuccess ? '✅' : '❌'
    def now = new Date().format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('Asia/Kolkata'))
    def jobUrl = "<a href=\"${env.BUILD_URL}\">${env.BUILD_URL}</a>"

    def title = customTitle ?: "Build #${env.BUILD_NUMBER} - ${status}"

    def slackMsg = """\
*${title}*

*Job:* `${env.JOB_NAME}`
*Triggered by:* *${state.buildTrigger}*
*Time (IST):* ${now}
*Priority:* *${env.PRIORITY}*
*Stages Passed:* *${state.passedStages}/${state.totalStages}*
${!isSuccess ? "*💥 Failed at:* *${state.failedStage}*\n*📛 Reason:* `${state.failureReason}`\n" : ""}
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
      <li><strong>Status:</strong> <span style=\"color:${isSuccess ? 'green' : 'red'}\">${status}</span></li>
      <li><strong>Triggered By:</strong> ${state.buildTrigger}</li>
      <li><strong>Time (IST):</strong> ${now}</li>
      <li><strong>Priority:</strong> ${env.PRIORITY}</li>
      <li><strong>Stages Passed:</strong> ${state.passedStages} / ${state.totalStages}</li>
      ${!isSuccess ? "<li><strong>Failed Stage:</strong> ${state.failedStage}</li><li><strong>Reason:</strong> ${state.failureReason}</li>" : ""}
      <li><strong>Build URL:</strong> <a href='${env.BUILD_URL}'>Click here</a></li>
    </ul>
  </body>
</html>
"""

    echo slackMsg

    mail(
        to: env.EMAIL_RECIPIENTS,
        subject: emailSubject,
        body: emailBody,
        mimeType: 'text/html'
    )

    slackSend(
        channel: env.SLACK_CHANNEL,
        color: color,
        message: slackMsg,
        tokenCredentialId: env.SLACK_CREDENTIAL_ID
    )
}
