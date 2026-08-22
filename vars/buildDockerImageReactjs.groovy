def call(Map config = [:]) {

    String image = config.image
    String tag = config.get('tag', 'latest')
    String dockerfile = config.get('dockerfile', 'reactjs.Dockerfile')
    String context = config.get('context', '.')

    if (!image) {
        error "buildDockerImage: image is required"
    }


    String dockerfileResource =
        libraryResource("dockerfiles/${dockerfile}")

    writeFile(
        file: dockerfile,
        text: dockerfileResource
    )

    sh """
        docker build -f ${dockerfile} -t ${image}:${tag} ${context}
    """
}