package uz.devapp.uzchat.data.model.request


import com.google.gson.annotations.SerializedName
import uz.devapp.uzchat.utils.PrefUtils

data class AddChatRequest(
    @SerializedName("receiver_user_id")
    val receiverUserId: Int,
    @SerializedName("sender_user_id")
    val senderUserId: Int=PrefUtils.getUser().id
)