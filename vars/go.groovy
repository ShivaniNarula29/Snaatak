def call(Map config) {
    def reportDir = config.reportDir
    def reportHtml = config.reportHtml
    def cacheDir = config.cacheDir
    def repoUrl = config.repoUrl
    def branch = config.branch
    def credentialsId = config.credentialsId
    def projectName = config.projectName

    stage('Clean Workspace') {
        cleanWs()
    }

    stage('Checkout Code') {
        git url: repoUrl, branch: branch, credentialsId: credentialsId
    }

    stage('Run OWASP Dependency Check') {
        withCredentials([string(credentialsId: 'owasp-api', variable: 'NVD_API_KEY')]) {
            sh """
                mkdir -p ${reportDir}
                /opt/dependency-check/bin/dependency-check.sh \\
                --project '${projectName}' \\
                --scan . \\
                --format HTML \\
                --out ${reportDir} \\
                --data ${cacheDir} \\
                --nvdApiKey=\${NVD_API_KEY}
            """
        }
    }

    stage('Archive and Publish Report') {
        archiveArtifacts artifacts: "${reportDir}/${reportHtml}", allowEmptyArchive: true

        publishHTML(
            reportDir: "${reportDir}",
            reportFiles: "${reportHtml}",
            reportName: 'OWASP Dependency Report',
            allowMissing: false,
            keepAll: true,
            alwaysLinkToLastBuild: true
        )
    }
}
