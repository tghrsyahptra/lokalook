package com.lokalook.lokalook.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.lokalook.lokalook.R
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import com.lokalook.lokalook.data.remote.response.userDataStore
import com.lokalook.lokalook.databinding.ActivityLoginBinding
import com.lokalook.lokalook.ui.ViewModelFactory
import com.lokalook.lokalook.ui.activities.MainActivity
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
            handleMessage(message)
        }

        loginViewModel.error.observe(this) { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleMessage(message: String) {
        if (message.isNotEmpty()) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            if (message == "Login successful!") {
                showLoginSuccessDialog()
            }
        }
        Log.d("LoginActivity", "Message: $message")
    }

    private fun showLoginSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.info))
            .setMessage(getString(R.string.login_success))
            .setIcon(R.drawable.ic_baseline_check_24)
            .setCancelable(false) // Prevent dialog from being dismissed by user
            .create()
            .apply {
                show()
                Handler(Looper.getMainLooper()).postDelayed({
                    dismiss()
                    navigateToMain()
                }, 2000)
            }
    }

    private fun navigateToMain() {
        Log.d("LoginActivity", "Navigating to MainActivity")
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
    }

    private fun setupActions() {
        binding.buttonLog.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (validateInput(email, password)) {
                loginViewModel.generateToken(
                    clientId = "your_client_id",
                    clientSecret = "your_client_secret",
                    email = email,
                    password = password
                )
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Email and Password cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        // Optional: Add email validation using regex
        val emailPattern = android.util.Patterns.EMAIL_ADDRESS
        if (!emailPattern.matcher(email).matches()) {
            Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
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