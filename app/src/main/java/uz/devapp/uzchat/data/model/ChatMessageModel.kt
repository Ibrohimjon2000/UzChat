package uz.devapp.uzchat.data.model


import com.google.gson.annotations.SerializedName

data class ChatMessageModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("messages")
    val messages: List<Message>,
    @SerializedName("receiver_user")
    val receiverUser: ReceiverUser,
    @SerializedName("receiver_user_id")
    val receiverUserId: Int,
    @SerializedName("sender_user_id")
    val senderUserId: Int
)