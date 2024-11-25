package com.lokalook.lokalook.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.lokalook.lokalook.R
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import com.lokalook.lokalook.databinding.ActivityLoginBinding
import com.lokalook.lokalook.ui.ViewModelFactory
import com.lokalook.lokalook.ui.login.LoginViewModel
import com.lokalook.lokalook.ui.activities.MainActivity
//import com.lokalook.lokalook.ui.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "User")

class LoginActivity : AppCompatActivity() {

    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewModel()
        setupActions()
        playAnimations()

//        binding.intRegister.setOnClickListener {
//            navigateToRegister()
//        }
    }

    private fun setupView() {
        hideStatusBar()
        supportActionBar?.hide()
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this, ViewModelFactory(UserPreferencesManager.getInstance(dataStore))
        )[LoginViewModel::class.java]

        loginViewModel.loginResult.observe(this) { login ->
            loginViewModel.saveUser(
                login.loginResult.name,
                login.loginResult.userId,
                login.loginResult.token
            )
        }

        loginViewModel.message.observe(this) { message ->
            if (message == "Login successful!") {
                showLoginSuccessDialog()
            }
        }

        loginViewModel.error.observe(this) { error ->
            handleLoginError(error)
        }

        loginViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun setupActions() {
        binding.buttonLog.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (validateInput(email, password)) {
                loginViewModel.login(email, password)
            }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty() -> {
                binding.edLoginEmail.error = getString(R.string.email_empty)
                false
            }
            password.isEmpty() -> {
                binding.edLoginPassword.error = getString(R.string.password_empty)
                false
            }
            password.length < 8 -> {
                binding.edLoginPassword.error = getString(R.string.password_short)
                false
            }
            else -> true
        }
    }

    private fun playAnimations() {
        val elements = listOf(
            binding.imLogo, binding.textLogin, binding.txEmail, binding.edLoginEmail,
            binding.txPassword, binding.edLoginPassword, binding.buttonLog, binding.intRegister
        )

        AnimatorSet().apply {
            elements.forEach {
                val animator = ObjectAnimator.ofFloat(it, View.ALPHA, 1f).setDuration(500)
                playSequentially(animator)
            }
            startDelay = 500
        }.start()
    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun showLoginSuccessDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.info))
            .setMessage(getString(R.string.login_success))
            .setIcon(R.drawable.ic_baseline_check_24)
            .setCancelable(false)
            .create()
            .apply {
                show()
                Handler(Looper.getMainLooper()).postDelayed({
                    dismiss()
                    navigateToMain()
                }, 2000)
            }
    }

    private fun handleLoginError(error: String) {
        val errorMessage = when (error) {
            "400" -> getString(R.string.label_invalid_email)
            "401" -> getString(R.string.user_not_found)
            else -> getString(R.string.unknown_error)
        }
        showErrorDialog(errorMessage)
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.info))
            .setMessage(message)
            .setIcon(R.drawable.ic_baseline_close_24)
            .setCancelable(false)
            .create()
            .apply {
                show()
                Handler(Looper.getMainLooper()).postDelayed({ dismiss() }, 2000)
            }
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent)
        finish()
    }

//    private fun navigateToRegister() {
//        val intent = Intent(this, RegisterActivity::class.java)
//        startActivity(intent)
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}