package uz.devapp.uzchat.screen.addchat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import uz.devapp.uzchat.R
import uz.devapp.uzchat.adapters.ChatListAdapter
import uz.devapp.uzchat.databinding.FragmentAddChatBinding
import uz.devapp.uzchat.screen.chat.ChatViewModel

interface AddChatCallback {
    fun updateChatList()
}

@AndroidEntryPoint
class AddChatFragment(val callback: AddChatCallback) : BottomSheetDialogFragment() {
    lateinit var binding: FragmentAddChatBinding
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddChatBinding.inflate(inflater, container, false)

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(requireActivity(), it, Toast.LENGTH_SHORT).show()
            binding.cardViewFriend.visibility = View.GONE
        }

        viewModel.progressLiveData.observe(this) {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnSearch.visibility = if (!it) View.VISIBLE else View.GONE
        }

        viewModel.searchFriendLiveData.observe(this) {
            binding.cardViewFriend.visibility = View.VISIBLE
            binding.tvFullname.text = it.fullname
            binding.tvPhone.text = it.phone
        }

        viewModel.addFriendLiveData.observe(this) {
            callback.updateChatList()
            dismiss()
        }

        binding.btnSearch.setOnClickListener {
            viewModel.searchFriend(binding.edPhone.text.toString())
        }

        binding.imgAdd.setOnClickListener {
            viewModel.addFriend(viewModel.searchFriendLiveData.value?.id ?: 0)
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(callback: AddChatCallback) = AddChatFragment(callback)
    }
}