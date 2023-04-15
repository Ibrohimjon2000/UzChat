package uz.devapp.uzchat.data.model

data class EventModel<T>(
    val command:Int,
    val data:T
)
