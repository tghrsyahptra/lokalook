package com.lokalook.lokalook.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lokalook.lokalook.R
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import com.lokalook.lokalook.data.remote.response.userDataStore
import com.lokalook.lokalook.databinding.ActivityLoginBinding
import com.lokalook.lokalook.ui.ViewModelFactory
import com.lokalook.lokalook.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupActions()
        playAnimations()

        binding.intRegister.setOnClickListener {
            navigateToRegister()
        }
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferencesManager.getInstance(applicationContext.userDataStore))
        )[LoginViewModel::class.java]

        loginViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        loginViewModel.message.observe(this) { message ->
            if (message.isNotEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.error.observe(this) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupActions() {
        binding.buttonLog.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginViewModel.generateToken(
                    clientId = "your_client_id",
                    clientSecret = "your_client_secret",
                    email = email,
                    password = password
                )
            } else {
                Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
    }


    private fun playAnimations() {
        val elements = listOf(
            binding.textLogin,
            binding.txEmail,
            binding.edLoginEmail,
            binding.txPassword,
            binding.edLoginPassword,
            binding.buttonLog,
            binding.intRegister
        )

        AnimatorSet().apply {
            elements.forEach {
                val animator = ObjectAnimator.ofFloat(it, View.ALPHA, 1f).setDuration(500)
                playSequentially(animator)
            }
            startDelay = 500
        }.start()
    }
}