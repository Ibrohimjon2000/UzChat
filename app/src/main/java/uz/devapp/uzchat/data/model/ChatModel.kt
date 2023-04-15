package uz.devapp.uzchat.data.model


import com.google.gson.annotations.SerializedName

data class ChatModel(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("receiver_user_id")
    val receiverUserId: Int,
    @SerializedName("sender_user_id")
    val senderUserId: Int,
    @SerializedName("time")
    val time: String,
    @SerializedName("user")
    val userModel: UserModel
):java.io.Serializable