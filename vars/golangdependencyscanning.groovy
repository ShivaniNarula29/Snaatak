def call(Map config) {
    def required = ['gitUrl', 'gitCredId', 'slackChannel', 'slackTokenCredentialId', 'emailRecipients']
    required.each { param ->
        if (!config[param]) {
            error "❌ Missing required config param: ${param}"
        }
    }

    node {
        def buildTrigger = ''
        def failedStage = ''
        def failureReason = ''
        def priority = 'P1'
        def now = new Date().format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('Asia/Kolkata'))
        def reportUrl = "${env.JOB_URL}${env.BUILD_NUMBER}/artifact/dependency-check-report/dependency-check-report.html"

        try {
            stage('Set JDK and PATH') {
                def jdkHome = tool name: 'JDK_17', type: 'hudson.model.JDK'
                env.PATH = "${jdkHome}/bin:${env.PATH}"
            }

            stage('Clean Workspace') {
                cleanWs()
            }

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
                dir('employee-api') {
                    try {
                        dependencyCheck additionalArguments: '--project "employee-api" --scan . --format HTML --out ./dependency-check-report --failOnCVSS 11',
                                        nvdCredentialsId: 'owasp',
                                        odcInstallation: 'owasp'

                        archiveArtifacts artifacts: 'dependency-check-report/**', allowEmptyArchive: true
                    } catch (err) {
                        failedStage = 'Dependency Check'
                        failureReason = err.message
                        error("❌ Dependency Check failed: ${failureReason}")
                    }
                }
            }

            buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'

            sendNotification(
                status: 'SUCCESS',
                buildTrigger: buildTrigger,
                failedStage: '',
                failureReason: '',
                reportUrl: reportUrl,
                now: now,
                config: config,
                priority: priority
            )

        } catch (err) {
            failedStage = failedStage ?: currentBuild.rawBuild.getExecution().getCurrentHeads()[0].getDisplayName()
            buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'
            failureReason = failureReason ?: err.message

            sendNotification(
                status: 'FAILURE',
                buildTrigger: buildTrigger,
                failedStage: failedStage,
                failureReason: failureReason,
                reportUrl: reportUrl,
                now: now,
                config: config,
                priority: priority
            )

            error("🚫 Pipeline failed at stage: ${failedStage}. Reason: ${failureReason}")
        } finally {
            echo "📄 Dependency scanning pipeline finished. Review above for status."
        }
    }
}

def sendNotification(Map args) {
    def isSuccess = (args.status == 'SUCCESS')
    def color = isSuccess ? 'good' : 'danger'
    def icon = isSuccess ? '✅' : '❌'
    def subject = "${icon} Jenkins Build Notification: ${args.status} - ${env.JOB_NAME} #${env.BUILD_NUMBER}"
    
    def emailBody = """
<html>
  <body>
    <h2>${icon} Jenkins Build Notification: ${args.status}</h2>
    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
    <p><strong>Build Number:</strong> #${env.BUILD_NUMBER}</p>
    <p><strong>Status:</strong> ${args.status}</p>
    <p><strong>Triggered By:</strong> ${args.buildTrigger}</p>
    <p><strong>Time (IST):</strong> ${args.now}</p>
    <p><strong>Priority:</strong> ${args.priority}</p>
    ${!isSuccess ? "<p><strong>Failed Stage:</strong> ${args.failedStage}</p>" : ""}
    <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
    ${isSuccess ? "<p><strong>Report:</strong> <a href='${args.reportUrl}'>Dependency Check Report</a></p>" : ""}
  </body>
</html>
"""

    def slackMsg = """\
${icon} Jenkins Build Notification: ${args.status}
*Job:* ${env.JOB_NAME}
*Build Number:* #${env.BUILD_NUMBER}
*Status:* ${args.status}
*Triggered By:* ${args.buildTrigger}
*Time (IST):* ${args.now}
*Priority:* ${args.priority}
${!isSuccess ? "*Failed Stage:* ${args.failedStage}" : "*Report:* ${args.reportUrl}"}
*Build URL:* ${env.BUILD_URL}
"""

    try {
        mail(
            to: args.config.emailRecipients,
            subject: subject,
            body: emailBody,
            mimeType: 'text/html'
        )
    } catch (err) {
        echo "⚠️ Failed to send email: ${err}"
    }

    try {
        slackSend(
            channel: args.config.slackChannel,
            color: color,
            message: slackMsg,
            tokenCredentialId: args.config.slackTokenCredentialId
        )
    } catch (err) {
        echo "⚠️ Failed to send Slack message: ${err}"
    }
}
