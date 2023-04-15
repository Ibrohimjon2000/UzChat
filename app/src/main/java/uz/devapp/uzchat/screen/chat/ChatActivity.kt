package uz.devapp.uzchat.screen.chat

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint
import io.socket.client.Socket
import uz.devapp.uzchat.R
import uz.devapp.uzchat.adapters.ChatListAdapter
import uz.devapp.uzchat.auth.LoginActivity
import uz.devapp.uzchat.databinding.ActivityChatBinding
import uz.devapp.uzchat.screen.addchat.AddChatCallback
import uz.devapp.uzchat.screen.addchat.AddChatFragment
import uz.devapp.uzchat.screen.info.InfoActivity
import uz.devapp.uzchat.utils.PrefUtils
import javax.inject.Inject

@AndroidEntryPoint
class ChatActivity : AppCompatActivity(), AddChatCallback {
    lateinit var binding: ActivityChatBinding
    private val viewModel: ChatViewModel by viewModels()

    @Inject
    lateinit var socket: Socket

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.progressLiveData.observe(this) {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.chatListLiveData.observe(this) {
            binding.lottie.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE

            val dividerItemDecoration =
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
            binding.rv.addItemDecoration(dividerItemDecoration)
            binding.rv.adapter = ChatListAdapter(it)
        }

        binding.add.setOnClickListener {
            val fragment = AddChatFragment(this)
            fragment.show(supportFragmentManager, fragment.tag)
        }

        binding.menu.setOnClickListener {
          openCloseNavigationDrawer()
        }

        binding.navView.setNavigationItemSelectedListener { item ->
            if (item.itemId == R.id.info) {
                startActivity(Intent(this, InfoActivity::class.java))
            } else if (item.itemId == R.id.call) {
                val profile_telegram =
                    Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/Mobiler007"))
                startActivity(profile_telegram)

            } else if (item.itemId == R.id.exit) {
                startActivity(
                    Intent(
                        this,
                        LoginActivity::class.java
                    )
                )
                finish()
                PrefUtils.setToken("")
            }
            false
        }

        loadData()
        initSocket()
    }

    fun loadData() {
        viewModel.getUser()
        viewModel.getChatList()
    }

    override fun updateChatList() {
        viewModel.getChatList()
    }

    fun initSocket() {
        socket.on(Socket.EVENT_CONNECT, {
            Log.d("IN", "Connected!")
            socket.emit(
                "authorization", PrefUtils.getToken()
            )
        })
        socket.on(Socket.EVENT_CONNECT_ERROR, {
            Log.d("IN", "Connect error ${it[0]}")
        })
        socket.on(Socket.EVENT_DISCONNECT, {
            Log.d("IN", "Connect error ${it[0]}")
        })
        socket.connect()
    }

    fun openCloseNavigationDrawer() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}