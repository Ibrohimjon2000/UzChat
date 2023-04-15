package uz.devapp.uzchat.data.model


import com.google.gson.annotations.SerializedName

data class Message(
    @SerializedName("chat_id")
    val chatId: Int,
    @SerializedName("created_at")
    val createdAt: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("owner_id")
    val ownerId: Int,
    @SerializedName("role")
    val role: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("updated_at")
    val updatedAt: Any,
    @SerializedName("user")
    val user: User
)