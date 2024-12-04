package com.lokalook.lokalook.ui.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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
import androidx.lifecycle.ViewModelProvider
import com.lokalook.lokalook.R
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import com.lokalook.lokalook.data.remote.response.userDataStore
import com.lokalook.lokalook.databinding.ActivityRegisterBinding
import com.lokalook.lokalook.ui.ViewModelFactory
import com.lokalook.lokalook.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializeView()
        initializeViewModel()
        setupActions()
        startAnimation()
    }

    private fun setupActions() {
        binding.buttonReg.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val username = binding.edRegisterUsername.text.toString()
            val address = binding.edRegisterAddress.text.toString()

            // Nilai Default
            val personalizationResult = "personalized result"
            val profileImage = "https://example.com/path/to/image.jpg"

            if (validateInput(name, email, password, username, address)) {
                registerViewModel.generateTokenAndRegister(
                    clientId = "YOUR_CLIENT_ID",
                    clientSecret = "YOUR_CLIENT_SECRET",
                    name = name,
                    email = email,
                    password = password,
                    username = username,
                    address = address,
                    personalizationResult = personalizationResult, // Default Value
                    profileImage = profileImage // Default Value
                )
            }
        }

        binding.intLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun initializeViewModel() {
        val userPreferencesManager = UserPreferencesManager.getInstance(userDataStore)
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(userPreferencesManager)
        )[RegisterViewModel::class.java]

        registerViewModel.message.observe(this) { handleSuccess(it) }
        registerViewModel.error.observe(this) { handleError(it) }
        registerViewModel.isLoading.observe(this) { toggleLoading(it) }
    }

    private fun initializeView() {
        hideStatusBar()
        supportActionBar?.hide()
    }

    private fun hideStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    private fun startAnimation() {
        val viewsToAnimate = listOf(
            binding.textRegister,
            binding.txName,
            binding.edRegisterName,
            binding.txEmail,
            binding.edRegisterEmail,
            binding.txPassword,
            binding.edRegisterPassword,
            binding.txUsername,
            binding.edRegisterUsername,
            binding.txAddress,
            binding.edRegisterAddress,
            binding.buttonReg,
            binding.intLogin
        )

        val animations = viewsToAnimate.map {
            ObjectAnimator.ofFloat(it, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        }

        AnimatorSet().apply {
            playSequentially(*animations.toTypedArray())
            startDelay = ANIMATION_DELAY
        }.start()
    }

    private fun validateInput(
        name: String,
        email: String,
        password: String,
        username: String,
        address: String
    ): Boolean {
        return when {
            name.isEmpty() -> {
                binding.edRegisterName.error = getString(R.string.name_empty)
                false
            }
            email.isEmpty() -> {
                binding.edRegisterEmail.error = getString(R.string.email_empty)
                false
            }
            password.isEmpty() -> {
                binding.edRegisterPassword.error = getString(R.string.password_empty)
                false
            }
            password.length < 8 -> {
                binding.edRegisterPassword.error = getString(R.string.password_short)
                false
            }
            username.isEmpty() -> {
                binding.edRegisterUsername.error = getString(R.string.username_empty)
                false
            }
            address.isEmpty() -> {
                binding.edRegisterAddress.error = getString(R.string.address_empty)
                false
            }
            else -> true
        }
    }

    private fun handleSuccess(message: String) {
        if (message == "201") {
            showAlert(getString(R.string.register_success)) {
                navigateToLogin()
            }
        }
    }

    private fun handleError(error: String) {
        if (error == "400") {
            showAlert(getString(R.string.register_failed))
        }
    }

    private fun showAlert(message: String, onDismiss: (() -> Unit)? = null) {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle(R.string.info)
            .setMessage(message)
            .setIcon(R.drawable.ic_baseline_check_24)
            .setCancelable(false)
            .create()

        alertDialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            alertDialog.dismiss()
            onDismiss?.invoke()
        }, ALERT_DIALOG_DELAY)
    }

    private fun toggleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val ANIMATION_DURATION = 500L
        private const val ANIMATION_DELAY = 2000L
        private const val ALERT_DIALOG_DELAY = 2000L
    }
}