package uz.devapp.uzchat.screen.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import uz.devapp.uzchat.auth.LoginActivity
import uz.devapp.uzchat.databinding.ActivitySplashBinding
import uz.devapp.uzchat.screen.chat.ChatActivity
import uz.devapp.uzchat.utils.PrefUtils

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            Handler().postDelayed({
                val intent = Intent(
                    this@SplashActivity,
                    if (PrefUtils.getToken()
                            .isNotEmpty()
                    ) ChatActivity::class.java else LoginActivity::class.java
                )
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }, 2000)
        }
    }
}