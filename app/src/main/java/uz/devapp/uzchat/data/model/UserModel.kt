package uz.devapp.uzchat.data.model


import com.google.gson.annotations.SerializedName

data class UserModel(
    @SerializedName("avatar")
    val avatar: Any,
    @SerializedName("fullname")
    val fullname: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("status")
    val status: String
):java.io.Serializable