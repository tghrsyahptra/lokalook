package com.lokalook.lokalook.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import com.lokalook.lokalook.ui.login.LoginViewModel


class ViewModelFactory(private val preferencesManager: UserPreferencesManager) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            LoginViewModel::class.java -> LoginViewModel(preferencesManager) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}