def call(Map config = [:]) {

    String sonarServerInstallation = config.get('sonarServerInstallation', 'SonarQube')
    String sonarqubeToken          = config.get('sonarqubeToken', 'sonar-token')
    String scannerImage            = config.get('scannerImage', 'sonarsource/sonar-scanner-cli')
    String projectKey              = config.projectKey
    String projectName             = config.get('projectName', projectKey)
    String sources                 = config.get('sources', '.')

    if (!projectKey) {
        error "scanSonarqube: projectKey is required"
    }

    withSonarQubeEnv(sonarServerInstallation) {
        withCredentials([
            string(
                credentialsId: sonarqubeToken,
                variable: 'SONAR_AUTH_TOKEN'
            )
        ]) {
            withEnv([
                "SONAR_SCANNER_IMAGE=${scannerImage}",
                "SONAR_PROJECT_KEY=${projectKey}",
                "SONAR_PROJECT_NAME=${projectName}",
                "SONAR_SOURCES=${sources}"
            ]) {
                sh '''
                    echo "Running SonarQube analysis..."

                    docker run --rm \
                        -v "$WORKSPACE:/usr/src" \
                        -w /usr/src \
                        -e SONAR_HOST_URL="$SONAR_HOST_URL" \
                        -e SONAR_AUTH_TOKEN="$SONAR_AUTH_TOKEN" \
                        "$SONAR_SCANNER_IMAGE" \
                        -Dsonar.projectKey="$SONAR_PROJECT_KEY" \
                        -Dsonar.projectName="$SONAR_PROJECT_NAME" \
                        -Dsonar.sources="$SONAR_SOURCES" \
                        -Dsonar.host.url="$SONAR_HOST_URL" \
                        -Dsonar.token="$SONAR_AUTH_TOKEN"
                '''
            }
        }
    }
}
