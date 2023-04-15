package uz.devapp.uzchat.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import uz.devapp.uzchat.data.api.Api
import uz.devapp.uzchat.data.model.request.LoginRequest
import uz.devapp.uzchat.data.model.request.RegistrationRequest
import uz.devapp.uzchat.data.model.response.UserResponse
import uz.devapp.uzchat.data.repository.sealed.DataResult

class AuthRepository(private val api: Api) : BaseRepository() {

    suspend fun login(phone: String, password: String): DataResult<UserResponse?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.login(LoginRequest(phone, password))
                return@withContext wrapResponse(response)
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.localizedMessage)
            }
        }
    }

    suspend fun registration(
        fullname: String,
        phone: String,
        password: String
    ): DataResult<UserResponse?> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.registration(RegistrationRequest(fullname, phone, password))
                return@withContext wrapResponse(response)
            } catch (e: Exception) {
                return@withContext DataResult.Error(e.localizedMessage)
            }
        }
    }
}