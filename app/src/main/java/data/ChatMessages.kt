package data


val SEND_BY_ME = "me"
val SEND_BY_AI = "others"

data class Message(val message: String, val sendBy : String){
    var SEND_BY_ME = "me"
    var SEND_BY_AI = "ai"

    fun getSendByMe(): String? {
        return SEND_BY_ME
    }

    fun setSendByMe(sendByMe: String) {
        SEND_BY_ME = sendByMe
    }

    fun getSendByAi(): String? {
        return SEND_BY_AI
    }

    fun setSendByAi(sendByAi: String) {
        SEND_BY_AI = sendByAi
    }
}
