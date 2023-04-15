package uz.devapp.uzchat.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import uz.devapp.uzchat.databinding.ActivityLoginBinding
import uz.devapp.uzchat.screen.chat.ChatActivity
import uz.devapp.uzchat.utils.PrefUtils

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.errorLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.progressLiveData.observe(this) {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnSign.visibility = if (!it) View.VISIBLE else View.GONE
        }

        viewModel.authListLiveData.observe(this) {
            PrefUtils.setToken(it.token)
            val intent = Intent(this, ChatActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.tvRegistration.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }

        binding.btnSign.setOnClickListener {
            viewModel.login(binding.edPhone.text.toString(), binding.edPassword.text.toString())
        }
    }
}