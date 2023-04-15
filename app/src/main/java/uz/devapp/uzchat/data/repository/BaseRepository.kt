package uz.devapp.uzchat.data.repository

import org.greenrobot.eventbus.EventBus
import retrofit2.Response
import uz.devapp.uzchat.data.model.EventModel
import uz.devapp.uzchat.data.model.response.BaseResponse
import uz.devapp.uzchat.data.repository.sealed.DataResult
import uz.devapp.uzchat.utils.Constants
import uz.devapp.uzchat.utils.PrefUtils

open class BaseRepository {
    fun <T> wrapResponse(response: Response<BaseResponse<T>>): DataResult<T> {
        return if (response.isSuccessful) {
            if (response.body()?.success == true) {
                DataResult.Success(response.body()!!.data!!)
            } else {
                if (response.body()?.errorCode == 1) {
                    PrefUtils.clear()
                    EventBus.getDefault().post(EventModel(Constants.EVENT_CLEAR_TOKEN,1))
                }
                DataResult.Error(response.body()?.message ?: "")
            }
        } else {
            DataResult.Error(response.message())
        }
    }
}