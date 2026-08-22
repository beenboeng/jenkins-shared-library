def call(Map config = [:]) {

    String image = config.image
    String tag = config.get('tag', 'latest')
    String credentialsId = config.get('credentialsId', 'DH_CREDIT')

    if (!image) {
        error "pushDockerImage: image is required"
    }

    if (!credentialsId) {
        error "pushDockerImage: credentialsId is required"
    }


   withCredentials([usernamePassword(
    credentialsId: credentialsId,
    usernameVariable: 'DOCKER_USERNAME',
    passwordVariable: 'DOCKER_PASSWORD'
    )]) {
        withEnv(["IMAGE=${image}", "TAG=${tag}"]) {
            sh '''
                echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
                docker push "$IMAGE:$TAG"
                docker logout
            '''
        }
    }

}
