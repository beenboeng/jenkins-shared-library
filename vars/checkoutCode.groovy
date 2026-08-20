def call(Map config = [:]) {

    String url = config.url
    String branch = config.get('branch', 'main')

    echo "Pulling code from ${url}"
    echo "Branch: ${branch}"

    git branch: branch, url: url
}