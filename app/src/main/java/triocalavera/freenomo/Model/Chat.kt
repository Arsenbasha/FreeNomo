package triocalavera.freenomo.Model

import java.util.*


data class Chat(
    val messageText: String? = null,
    val messageUser: String? = null,
    val username:String,
    val uidDestino:String,
    private var messageTime: Long = 0
) {
    init {
        messageTime = Date().time
    }
}
