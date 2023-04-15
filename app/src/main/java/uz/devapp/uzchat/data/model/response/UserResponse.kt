package uz.devapp.uzchat.data.model.response


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("avatar")
    val avatar: Any,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("token")
    val token: String
)