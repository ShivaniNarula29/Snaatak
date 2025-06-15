def call(Map config = [:]) {
    def status = config.status
    def testScope = config.scope
    def buildTrigger = config.buildTrigger
    def failureReason = config.failureReason
    def failedStage = config.failedStage
    def slackChannel = config.slackChannel
    def slackCredId = config.slackCredId
    def emailTo = config.emailTo
    def priority = config.priority

    def isSuccess = (status == 'SUCCESS')
    def color = isSuccess ? 'good' : 'danger'
    def icon = isSuccess ? '✅' : '❌'
    def now = new Date().format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('Asia/Kolkata'))

    def testStageMessage = testScope == 'ALL' ? 'Attendance API & Notification Worker Unit Tests' :
                           testScope == 'ATTENDANCE' ? 'Attendance API Unit Tests' :
                           'Notification Worker Unit Tests'

    def slackMsg = """${icon} *Build #${env.BUILD_NUMBER} - ${status}*
*Job:* `${env.JOB_NAME}`
*Triggered by:* *${buildTrigger}*
*Time (IST):* ${now}
*Priority:* *${priority}*
*Stages:* ${testStageMessage}
${!isSuccess ? "*💥 Issue:* `${failureReason}`\n*Failed Stage:* ${failedStage}\n" : ""}
*🔗 Build URL:* <${env.BUILD_URL}|View Build>
*📦 Artifacts:* <${env.BUILD_URL}artifact/|Download Test Logs>"""

    def emailSubject = "${icon} ${status}: ${env.JOB_NAME} #${env.BUILD_NUMBER}${!isSuccess ? " [${priority}]" : ""}"

    def emailBody = """
<html>
  <body>
    <h2>${icon} Jenkins Build Notification: ${status}</h2>
    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
    <p><strong>Build Number:</strong> #${env.BUILD_NUMBER}</p>
    <p><strong>Status:</strong> <span style="color:${isSuccess ? 'green' : 'red'}">${status}</span></p>
    <p><strong>Triggered By:</strong> ${buildTrigger}</p>
    <p><strong>Time (IST):</strong> ${now}</p>
    <p><strong>Priority:</strong> ${priority}</p>
    ${!isSuccess ? "<p><strong>Failure Reason:</strong> ${failureReason}</p><p><strong>Failed Stage:</strong> ${failedStage}</p>" : ""}
    <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
    <p><strong>Artifacts:</strong> <a href="${env.BUILD_URL}artifact/">Download Test Logs</a></p>
  </body>
</html>"""

    mail(to: emailTo, subject: emailSubject, body: emailBody, mimeType: 'text/html')
    slackSend(channel: slackChannel, color: color, message: slackMsg, tokenCredentialId: slackCredId)
}
