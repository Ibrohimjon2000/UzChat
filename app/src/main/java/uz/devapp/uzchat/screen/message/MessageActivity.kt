package uz.devapp.uzchat.screen.message

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import uz.devapp.uzchat.adapters.MessageAdapter
import uz.devapp.uzchat.data.model.ChatModel
import uz.devapp.uzchat.data.model.request.SendMessageRequest
import uz.devapp.uzchat.databinding.ActivityMessageBinding
import uz.devapp.uzchat.screen.chat.ChatViewModel
import uz.devapp.uzchat.utils.Constants
import uz.devapp.uzchat.utils.PrefUtils
import javax.inject.Inject

@AndroidEntryPoint
class MessageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMessageBinding
    private val viewModel: ChatViewModel by viewModels()
    lateinit var chatModel: ChatModel

    @Inject
    lateinit var socket: Socket
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            chatModel = intent.getSerializableExtra(Constants.EXTRA_DATA) as ChatModel
            tvFullname.text = chatModel.userModel.fullname

            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                finish()
            }

            viewModel.errorLiveData.observe(this@MessageActivity) {
                Toast.makeText(this@MessageActivity, it, Toast.LENGTH_SHORT).show()
            }

            viewModel.chatLiveData.observe(this@MessageActivity) {
                binding.lottie.visibility = if (it.messages.isEmpty()) View.VISIBLE else View.GONE
                rv.adapter = MessageAdapter(it.messages)
                rv.postDelayed({
                    scrollView.fullScroll(View.FOCUS_DOWN)
                }, 50)
            }

            send.setOnClickListener {
                if (edSendText.text.toString().isNotEmpty()) {
                    socket.emit(
                        "send",
                        Gson().toJson(
                            SendMessageRequest(
                                PrefUtils.getToken(),
                                chatModel.id,
                                edSendText.text.toString()
                            )
                        )
                    )
                }
                edSendText.setText("")
                viewModel.getChatMessages(chatModel.id)
            }

            edSendText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun afterTextChanged(p0: Editable?) {
                    if (p0.toString().isNotEmpty()) {
                        send.setColorFilter(Color.parseColor("#000000"))
                    } else {
                        send.setColorFilter(Color.parseColor("#9F9F9F"))
                    }
                }
            })

            viewModel.getChatMessages(chatModel.id)
            initSocket()
        }
    }

    fun initSocket() {
        socket.on("new_message") {
            viewModel.getChatMessages(chatModel.id)
        }
    }
}