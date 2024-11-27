package com.lokalook.lokalook.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import com.lokalook.lokalook.ui.login.LoginViewModel
import com.lokalook.lokalook.ui.activities.MainViewModel
import com.lokalook.lokalook.ui.register.RegisterViewModel


class ViewModelFactory(private val preferencesManager: UserPreferencesManager) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginViewModel::class.java -> LoginViewModel(preferencesManager) as T
            MainViewModel::class.java -> MainViewModel(preferencesManager) as T
            RegisterViewModel::class.java -> RegisterViewModel(preferencesManager) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}