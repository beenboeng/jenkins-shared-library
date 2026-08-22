def call(Map config = [:]) {

    String image = config.image
    String tag = config.get('tag', 'latest')
    String containerName = config.containerName
    String hostPort = config.hostPort
    String containerPort = config.get('containerPort', '80')

    if (!image) {
        error "deployDockerContainer: image is required"
    }

    if (!containerName) {
        error "deployDockerContainer: containerName is required"
    }

    if (!hostPort) {
        error "deployDockerContainer: hostPort is required"
    }

    sh """
        docker stop ${containerName} || true 
        docker rm ${containerName} || true 

        docker run -dp ${hostPort}:${containerPort} --name ${containerName} "${image}:${tag}"
    """
}
