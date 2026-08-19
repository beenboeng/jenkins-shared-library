pushDockerImage(
    image: 'theghost007/my-react-app',
    tag: "${BUILD_NUMBER}",
    credentialsId: 'dockerhub'
)