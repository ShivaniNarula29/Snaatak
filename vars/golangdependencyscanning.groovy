def call(Map config) {
    def required = ['gitUrl', 'gitCredId', 'slackChannel', 'slackTokenCredentialId', 'emailRecipients']
    for (r in required) {
        if (!config[r]) {
            error "Missing required config param: ${r}"
        }
    }

    node {
        def passedStages = 0
        def failedStage = 'Unknown'
        def buildTrigger = ''
        def now = new Date().format("yyyy-MM-dd HH:mm:ss", TimeZone.getTimeZone('Asia/Kolkata'))
        def priority = 'P1'
        def currentStage = ''
        def failureReason = ''
        def reportUrl = "${env.JOB_URL}${env.BUILD_NUMBER}/artifact/dependency-check-report/dependency-check-report.html"

        try {
            stage('Set JDK and PATH') {
                def jdkHome = tool name: 'JDK_17', type: 'hudson.model.JDK'
                env.PATH = "${jdkHome}/bin:${env.PATH}"
            }

            stage('Clean Workspace') {
                cleanWs()
                passedStages++
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
                passedStages++
            }

            stage('Dependency Check') {
                currentStage = 'Dependency Check'
                try {
                    dir('employee-api') {
                        dependencyCheck additionalArguments: '--project "employee-api" --scan . --format HTML --out ./dependency-check-report --failOnCVSS 10',
                                        nvdCredentialsId: 'owasp',
                                        odcInstallation: 'owasp'

                        archiveArtifacts artifacts: 'dependency-check-report/**', allowEmptyArchive: true
                    }
                    passedStages++
                } catch (err) {
                    echo "❌ Dependency Check Exception: ${err}"
                    echo "❌ Stack trace: ${err.getStackTrace().join('\n')}"
                    failureReason = "Dependency Check failed: ${err.message}"
                    failedStage = currentStage
                    error(failureReason)
                }
            }

            buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'

            def emailBody = """
<html>
  <body>
    <h2>✅ Jenkins Build Notification: SUCCESS</h2>
    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
    <p><strong>Build Number:</strong> #${env.BUILD_NUMBER}</p>
    <p><strong>Status:</strong> SUCCESS</p>
    <p><strong>Triggered By:</strong> ${buildTrigger}</p>
    <p><strong>Time (IST):</strong> ${now}</p>
    <p><strong>Priority:</strong> ${priority}</p>
    <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
    <p><strong>Report:</strong> <a href="${reportUrl}">Dependency Check Report</a></p>
  </body>
</html>
"""

            def slackMsg = """
✅ Jenkins Build Notification: SUCCESS
*Job:* ${env.JOB_NAME}
*Build Number:* #${env.BUILD_NUMBER}
*Status:* SUCCESS
*Triggered By:* ${buildTrigger}
*Time (IST):* ${now}
*Priority:* ${priority}
*Build URL:* ${env.BUILD_URL}
*Report:* ${reportUrl}
"""

            try {
                mail(
                    to: config.emailRecipients,
                    subject: "✅ Jenkins Build Notification: SUCCESS - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: emailBody,
                    mimeType: 'text/html'
                )
            } catch (err) {
                echo "⚠️ Email sending failed: ${err}"
            }

            try {
                slackSend(
                    channel: config.slackChannel,
                    color: 'good',
                    message: slackMsg,
                    tokenCredentialId: config.slackTokenCredentialId
                )
            } catch (err) {
                echo "⚠️ Slack sending failed: ${err}"
            }

        } catch (Exception e) {
            failedStage = currentBuild.rawBuild.getExecution().getCurrentHeads()[0].getDisplayName()
            buildTrigger = currentBuild.getBuildCauses()?.getAt(0)?.userName ?: 'Auto-triggered'

            def emailBody = """
<html>
  <body>
    <h2>❌ Jenkins Build Notification: FAILURE</h2>
    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
    <p><strong>Build Number:</strong> #${env.BUILD_NUMBER}</p>
    <p><strong>Status:</strong> FAILURE</p>
    <p><strong>Triggered By:</strong> ${buildTrigger}</p>
    <p><strong>Time (IST):</strong> ${now}</p>
    <p><strong>Priority:</strong> ${priority}</p>
    <p><strong>Failed Stage:</strong> ${failedStage}</p>
    <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
  </body>
</html>
"""

            def slackMsg = """
❌ Jenkins Build Notification: FAILURE
*Job:* ${env.JOB_NAME}
*Build Number:* #${env.BUILD_NUMBER}
*Status:* FAILURE
*Triggered By:* ${buildTrigger}
*Time (IST):* ${now}
*Priority:* ${priority}
*Failed Stage:* ${failedStage}
*Build URL:* ${env.BUILD_URL}
"""

            try {
                mail(
                    to: config.emailRecipients,
                    subject: "❌ Jenkins Build Notification: FAILURE - ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: emailBody,
                    mimeType: 'text/html'
                )
            } catch (err) {
                echo "⚠️ Email sending failed: ${err}"
            }

            try {
                slackSend(
                    channel: config.slackChannel,
                    color: 'danger',
                    message: slackMsg,
                    tokenCredentialId: config.slackTokenCredentialId
                )
            } catch (err) {
                echo "⚠️ Slack sending failed: ${err}"
            }

            error("Pipeline failed at stage: ${failedStage}. See logs for details.")
        } finally {
            echo "📄 Dependency scanning pipeline finished. Check status above."
        }
    }
}
