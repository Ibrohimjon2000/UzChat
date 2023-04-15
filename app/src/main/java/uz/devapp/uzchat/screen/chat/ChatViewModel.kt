package uz.devapp.uzchat.screen.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import uz.devapp.uzchat.data.model.ChatMessageModel
import uz.devapp.uzchat.data.model.ChatModel
import uz.devapp.uzchat.data.model.UserModel
import uz.devapp.uzchat.data.model.response.UserResponse
import uz.devapp.uzchat.data.repository.MainRepository
import uz.devapp.uzchat.data.repository.sealed.DataResult
import uz.devapp.uzchat.utils.PrefUtils
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private var _errorLiveData = MutableLiveData<String>()
    var errorLiveData: LiveData<String> = _errorLiveData

    private var _progressLiveData = MutableLiveData<Boolean>()
    var progressLiveData: LiveData<Boolean> = _progressLiveData

    private var _chatListLiveData = MutableLiveData<List<ChatModel>>()
    var chatListLiveData: LiveData<List<ChatModel>> = _chatListLiveData

    private var _searchFriendLiveData = MutableLiveData<UserModel>()
    var searchFriendLiveData: LiveData<UserModel> = _searchFriendLiveData

    private var _addFriendLiveData = MutableLiveData<Any>()
    var addFriendLiveData: LiveData<Any> = _addFriendLiveData

    private var _chatLiveData = MutableLiveData<ChatMessageModel>()
    var chatLiveData: LiveData<ChatMessageModel> = _chatLiveData

    fun getUser() {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.getUser()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    PrefUtils.setUser(result.result)
                }
            }
            _progressLiveData.value = false
        }
    }

    fun getChatList() {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.getChatList()
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _chatListLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }

    fun searchFriend(phone:String) {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.searchFriend(phone)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _searchFriendLiveData.value = (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }

    fun addFriend(id:Int) {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.addFriend(id)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _addFriendLiveData.value = true
                }
            }
            _progressLiveData.value = false
        }
    }

    fun getChatMessages(id: Int) {
        viewModelScope.launch {
            _progressLiveData.value = true
            val result = repository.getChatMessages(id)
            when (result) {
                is DataResult.Error -> {
                    _errorLiveData.value = result.message
                }
                is DataResult.Success -> {
                    _chatLiveData.value= (result.result)
                }
            }
            _progressLiveData.value = false
        }
    }
}