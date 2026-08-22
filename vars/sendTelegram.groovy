def call(Map config = [:]) {

    String tokenCredentialsId = config.get('tokenCredentialsId', 'TELEGRAM_BOT_TOKEN')
    String chatId  = config.chatId
    String message = config.message
    String parseMode = config.get('parseMode', 'Markdown')

    if (!chatId) {
        error "sendTelegram: chatId is required"
    }

    if (!message) {
        error "sendTelegram: message is required"
    }

    withCredentials([string(credentialsId: tokenCredentialsId, variable: 'TG_TOKEN')]) {
        withEnv(["TG_CHAT_ID=${chatId}", "TG_MESSAGE=${message}", "TG_PARSE_MODE=${parseMode}"]) {
            sh '''
                curl -fsS -o /dev/null -X POST \
                    "https://api.telegram.org/bot$TG_TOKEN/sendMessage" \
                    --data-urlencode "chat_id=$TG_CHAT_ID" \
                    --data-urlencode "parse_mode=$TG_PARSE_MODE" \
                    --data-urlencode "text=$TG_MESSAGE"
            '''
        }
    }
}