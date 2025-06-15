// vars/genericnotificaiton.groovy
def call(Map config = [:]) {
    node {
        def passedStages = 0
        def failedStage = ''
        def failureReason = ''
        def currentStage = ''
        def totalStages = 4
        def buildStatus = 'SUCCESS'

        def SLACK_CHANNEL = config.slackChannel ?: 'notificationn-channel'
        def SLACK_CREDENTIAL_ID = config.slackCredentialId ?: 'downtime-crew'
        def EMAIL_RECIPIENTS = config.emailRecipients ?: 'shivani.narula.snaatak@mygurukulam.co'
        def PRIORITY = config.priority ?: 'P1'
        def buildTrigger = ''

        timestamps {
            try {
                stage('Initialize') {
                    buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'
                    currentBuild.description = ''
                }

                stage('Cred Scanning') {
                    currentStage = 'Cred Scanning'
                    try {
                        echo "Running credential scan..."
                        sh 'echo "No credentials found"'
                        currentBuild.description += "\n✅ Cred Scanning: Success"
                        passedStages++
                    } catch (e) {
                        failedStage = currentStage
                        failureReason = "Cred Scanning failed: ${e.message}"
                        currentBuild.description += "\n❌ Cred Scanning: ${e.message}"
                        buildStatus = 'FAILURE'
                        error(failureReason)
                    }
                }

                stage('License Scanning') {
                    currentStage = 'License Scanning'
                    try {
                        echo "Running license scan..."
                        sh 'echo "All licenses compliant"'
                        currentBuild.description += "\n✅ License Scanning: Success"
                        passedStages++
                    } catch (e) {
                        failedStage = currentStage
                        failureReason = "License Scanning failed: ${e.message}"
                        currentBuild.description += "\n❌ License Scanning: ${e.message}"
                        buildStatus = 'FAILURE'
                        error(failureReason)
                    }
                }

                stage('AMI Build') {
                    currentStage = 'AMI Build'
                    try {
                        echo "Building AMI..."
                        sh 'echo "AMI built successfully"'
                        currentBuild.description += "\n✅ AMI Build: Success"
                        passedStages++
                    } catch (e) {
                        failedStage = currentStage
                        failureReason = "AMI Build failed: ${e.message}"
                        currentBuild.description += "\n❌ AMI Build: ${e.message}"
                        buildStatus = 'FAILURE'
                        error(failureReason)
                    }
                }

                stage('Commit Sign-off') {
                    currentStage = 'Commit Sign-off'
                    try {
                        echo "Checking commit sign-off..."
                        // Uncomment if needed
                        // sh 'git log -1 --pretty=%B | grep -q "Signed-off-by:"'
                        currentBuild.description += "\n✅ Commit Sign-off: Success"
                        passedStages++
                    } catch (e) {
                        failedStage = currentStage
                        failureReason = "Commit Sign-off check failed: ${e.message}"
                        currentBuild.description += "\n❌ Commit Sign-off: ${e.message}"
                        buildStatus = 'FAILURE'
                        error(failureReason)
                    }
                }
            } catch (err) {
                if (!failedStage) {
                    failedStage = currentStage ?: 'Unknown Stage'
                    failureReason = failureReason ?: 'Unknown failure reason'
                }
                buildStatus = 'FAILURE'
                throw err
            } finally {
                stage('Notify on Slack/E-Mail') {
                    notifyBuildStatus(
                        buildStatus, passedStages, totalStages,
                        failedStage, failureReason,
                        buildTrigger, SLACK_CHANNEL,
                        SLACK_CREDENTIAL_ID, EMAIL_RECIPIENTS, PRIORITY
                    )
                }
                echo "📄 Pipeline execution completed."
            }
        }
    }
}

def notifyBuildStatus(String status, int passedStages, int totalStages, String failedStage, String failureReason, String buildTrigger, String slackChannel, String slackTokenId, String emailRecipients, String priority) {
    def isSuccess = (status == 'SUCCESS')
    def color = isSuccess ? 'good' : 'danger'
    def icon = isSuccess ? '✅' : '❌'
    def now = new Date().format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('Asia/Kolkata'))
    def jobUrl = "<a href=\"${env.BUILD_URL}\">${env.BUILD_URL}</a>"

    def slackMsg = """\
${icon} *Build #${env.BUILD_NUMBER} - ${status}*

*Job:* `${env.JOB_NAME}`
*Triggered by:* *${buildTrigger}*
*Time (IST):* ${now}
*Priority:* *${priority}*
*Stages Passed:* *${passedStages}/${totalStages}*
${!isSuccess ? "*💥 Failed at:* *${failedStage}*\n*📛 Reason:* `${failureReason}`\n" : ""}
*🔗 Build URL:* <${env.BUILD_URL}|View Build>
"""

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
    <p><strong>Stages Passed:</strong> ${passedStages} / ${totalStages}</p>
    ${!isSuccess ? "<p><strong>Failed Stage:</strong> ${failedStage}</p><p><strong>Reason:</strong> ${failureReason}</p>" : ""}
    <p><strong>Build URL:</strong> ${jobUrl}</p>
  </body>
</html>
"""

    echo slackMsg

    mail(
        to: emailRecipients,
        subject: emailSubject,
        body: emailBody,
        mimeType: 'text/html'
    )

    slackSend(
        channel: slackChannel,
        color: color,
        message: slackMsg,
        tokenCredentialId: slackTokenId
    )
}
