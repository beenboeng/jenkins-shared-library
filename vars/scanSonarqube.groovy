def call(Map config = [:]) {

    String sonarqubeToken = config.get('sonarqubeToken', 'SONARQUBE-TOKEN')
    String sonarScannerInstallation = config.get('sonarScannerInstallation', 'sonar-scanner')
    String sonarServerInstallation = config.get('sonarServerInstallation', sonarScannerInstallation)
    String projectName = config.projectName
    String projectVersion = config.get('projectVersion', '1.0.0')
    String projectKey = config.projectKey

    if (!projectName) {
        error "scanSonarqube: projectName is required"
    }

    if (!projectKey) {
        error "scanSonarqube: projectKey is required"
    }

    String scannerHome = tool(sonarScannerInstallation)

    withSonarQubeEnv(credentialsId: sonarqubeToken, installationName: sonarServerInstallation) {
        sh """
            ${scannerHome}/bin/sonar-scanner \
                -Dsonar.projectName=${projectName} \
                -Dsonar.projectKey=${projectKey} \
                -Dsonar.projectVersion=${projectVersion}
        """
    }
}
