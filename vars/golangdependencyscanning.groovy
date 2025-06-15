def call(Map config) {
    def required = ['gitUrl', 'gitCredId', 'slackChannel', 'slackTokenCredentialId', 'emailRecipients']
    required.each { if (!config[it]) error("Missing required config param: ${it}") }

    node {
        def failureReason = ''
        def failedStage = ''
        def buildTrigger = ''
        def now = new Date().format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('Asia/Kolkata'))
        def priority = 'P1'
        def reportUrl = "${env.BUILD_URL}artifact/employee-api/dependency-check-report/dependency-check-report.html"

        try {
            stage('Set JDK and PATH') {
                def jdkHome = tool name: 'JDK_17', type: 'hudson.model.JDK'
                env.PATH = "${jdkHome}/bin:${env.PATH}"
            }

            stage('Clean Workspace') { cleanWs() }

            stage('Checkout Employee-api Repo') {
                dir('employee-api') {
                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: 'main']],
                        userRemoteConfigs: [[
                            url: config.gitUrl,
                            credentialsId: config.gitCredId
                        ]]
                    ])
                }
            }

            stage('Dependency Check') {
                failedStage = 'Dependency Check'
                dir('employee-api') {
                    try {
                        dependencyCheck additionalArguments: '--project "employee-api" --scan . --format HTML --out ./dependency-check-report',
                                        nvdCredentialsId: 'owasp',
                                        odcInstallation: 'owasp'

                        archiveArtifacts artifacts: 'dependency-check-report/**', allowEmptyArchive: true
                    } catch (err) {
                        echo "❌ Dependency Check Exception: ${err}"
                        echo "❌ Stack trace:\n${err.stackTrace.join('\n')}"
                        failureReason = err.message
                        error("Dependency Check failed: ${failureReason}")
                    }
                }
            }

            // Success notification
            buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'
            sendNotification(config, 'SUCCESS', buildTrigger, '', '', reportUrl, now, priority)

        } catch (err) {
            // Failure notification
            buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'
            sendNotification(config, 'FAILURE', buildTrigger, failedStage, failureReason, reportUrl, now, priority)
            error("Pipeline failed at stage: ${failedStage}. See above logs.")
        } finally {
            echo "📄 Dependency scanning pipeline finished."
        }
    }
}

def sendNotification(cfg, status, trigger, failedStage, reason, reportUrl, now, priority) {
    def isSuccess = (status == 'SUCCESS')
    def icon = isSuccess ? '✅' : '❌'
    def color = isSuccess ? 'good' : 'danger'
    def subject = "${icon} Jenkins Build Notification: ${status} - ${env.JOB_NAME} #${env.BUILD_NUMBER}"
    def emailBody = """
<html><body>
<h2>${icon} Jenkins Build Notification: ${status}</h2>
<p><strong>Job:</strong> ${env.JOB_NAME}</p>
<p><strong>Build Number:</strong> #${env.BUILD_NUMBER}</p>
<p><strong>Status:</strong> ${status}</p>
<p><strong>Triggered By:</strong> ${trigger}</p>
<p><strong>Time (IST):</strong> ${now}</p>
<p><strong>Priority:</strong> ${priority}</p>
${isSuccess ? "<p><strong>Report:</strong> <a href='${reportUrl}'>Dependency Check Report</a></p>" : "<p><strong>Failed Stage:</strong> ${failedStage}<br><strong>Reason:</strong> ${reason}</p>"}
<p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
</body></html>
"""
    def slackMsg = """\
${icon} *${status}* - Build #${env.BUILD_NUMBER}
• *Job:* ${env.JOB_NAME}
• *Triggered By:* ${trigger}
${isSuccess ? "• *Report:* ${reportUrl}" : "• *Failed Stage:* ${failedStage} — ${reason}"}
• *Build URL:* ${env.BUILD_URL}
"""

    try {
        mail(to: cfg.emailRecipients, subject: subject, body: emailBody, mimeType: 'text/html')
    } catch (mailErr) { echo "⚠️ Email failed: ${mailErr}" }

    try {
        slackSend(
            channel: cfg.slackChannel,
            color: color,
            message: slackMsg,
            tokenCredentialId: cfg.slackTokenCredentialId
        )
    } catch (slackErr) { echo "⚠️ Slack failed: ${slackErr}" }
}
