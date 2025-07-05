package com.thecode.vietglobe.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.thecode.vietglobe.databinding.ActivitySplashBinding
import com.thecode.vietglobe.presentation.main.MainActivity
import com.thecode.vietglobe.presentation.onboarding.OnboardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()
    private lateinit var binding: ActivitySplashBinding
    private val SPLASH_TIME_OUT: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        lifecycleScope.launch {
            delay(SPLASH_TIME_OUT)
            viewModel.isOnboardingCompleted.collect { completed ->
                if (completed) {
                    goToMainActivity()
                } else {
                    goToOnboardingActivity()
                }
            }
        }
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToOnboardingActivity() {
        val intent = Intent(this, OnboardingActivity::class.java)
        startActivity(intent)
        finish()
    }
}
