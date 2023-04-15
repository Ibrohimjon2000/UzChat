package uz.devapp.uzchat.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import uz.devapp.uzchat.databinding.ActivityRegistrationBinding
import uz.devapp.uzchat.screen.chat.ChatActivity
import uz.devapp.uzchat.utils.PrefUtils

@AndroidEntryPoint
class RegistrationActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegistrationBinding
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                finish()
            }
        }
        viewModel.errorLiveData.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        viewModel.progressLiveData.observe(this) {
            binding.flProgress.visibility = if (it) View.VISIBLE else View.GONE
            binding.btnRegistration.visibility = if (!it) View.VISIBLE else View.GONE
        }

        viewModel.authListLiveData.observe(this) {
            PrefUtils.setToken(it.token)
            val intent = Intent(this, ChatActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnRegistration.setOnClickListener {
            if (binding.edPassword.text.toString() != binding.edRepassword.text.toString()) {
                Toast.makeText(this, "Please enter the correct password!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            viewModel.registration(
                binding.edFullname.text.toString(),
                binding.edPhone.text.toString(),
                binding.edPassword.text.toString()
            )
        }
    }
}