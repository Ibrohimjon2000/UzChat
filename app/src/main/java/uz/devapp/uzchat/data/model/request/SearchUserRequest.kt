package uz.devapp.uzchat.data.model.request


import com.google.gson.annotations.SerializedName

data class SearchUserRequest(
    @SerializedName("phone")
    val phone: String
)