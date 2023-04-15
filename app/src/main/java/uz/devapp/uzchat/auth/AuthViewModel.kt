package uz.devapp.uzchat.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.devapp.uzchat.data.model.response.UserResponse
import uz.devapp.uzchat.data.repository.AuthRepository
import uz.devapp.uzchat.data.repository.sealed.DataResult
import uz.devapp.uzchat.utils.PrefUtils
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository):ViewModel() {

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _authListLiveData = MutableLiveData<UserResponse>()
    var authListLiveData: LiveData<UserResponse> = _authListLiveData

    fun login(phone:String,password:String) {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.login(phone,password)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _authListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }

    fun registration(fullname:String, phone:String,password:String) {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.registration(fullname, phone, password)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _authListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}