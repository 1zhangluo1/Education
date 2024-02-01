package data

data class ChatMessages(var message: String, var sendBy: String) {
    companion object {
        val sendByMe = "me"
        val sendByOthers = "others"
    }

}