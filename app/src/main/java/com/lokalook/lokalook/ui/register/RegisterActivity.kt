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
import com.lokalook.lokalook.ui.register.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        initializeView()
        initializeViewModel()
        setupActions()
        startAnimation()
    }

    private fun setupActions() {
        binding?.buttonReg?.setOnClickListener {
            val name = binding?.edRegisterName?.text.toString()
            val email = binding?.edRegisterEmail?.text.toString()
            val password = binding?.edRegisterPassword?.text.toString()

            if (validateInput(name, email, password)) {
                registerViewModel.register(name, email, password)
            }
        }

        binding?.intLogin?.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun initializeViewModel() {
        registerViewModel = ViewModelProvider(this, ViewModelFactory(UserPreferencesManager.getInstance(userDataStore))).get(
            RegisterViewModel::class.java)

        registerViewModel.run {
            message.observe(this@RegisterActivity) { handleSuccess(it) }
            error.observe(this@RegisterActivity) { handleError(it) }
            isLoading.observe(this@RegisterActivity) { toggleLoading(it) }
        }
    }

    private fun initializeView() {
        hideStatusBar()
        supportActionBar?.hide()
    }

    private fun hideStatusBar() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }

    private fun startAnimation() {
        val viewsToAnimate = listOf(
            binding?.textRegister,
            binding?.txName,
            binding?.edRegisterName,
            binding?.txEmail,
            binding?.edRegisterEmail,
            binding?.txPassword,
            binding?.edRegisterPassword,
            binding?.buttonReg,
            binding?.intLogin
        )

        val animations = viewsToAnimate.map {
            ObjectAnimator.ofFloat(it, View.ALPHA, 1f).setDuration(ANIMATION_DURATION)
        }

        AnimatorSet().apply {
            playSequentially(*animations.toTypedArray())
            startDelay = ANIMATION_DELAY
        }.start()
    }

    private fun validateInput(name: String, email: String, password: String): Boolean {
        return when {
            name.isEmpty() -> {
                binding?.edRegisterName?.error = getString(R.string.name_empty)
                false
            }
            email.isEmpty() -> {
                binding?.edRegisterEmail?.error = getString(R.string.email_empty)
                false
            }
            password.isEmpty() -> {
                binding?.edRegisterPassword?.error = getString(R.string.password_empty)
                false
            }
            password.length < 8 -> {
                binding?.edRegisterPassword?.error = getString(R.string.password_short)
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
        binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
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