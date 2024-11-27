package com.lokalook.lokalook.ui.activities

import android.util.Log
import androidx.lifecycle.ViewModel
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import retrofit2.Response

class MainViewModel(private val userPreference: UserPreferencesManager) : ViewModel() {

    private fun logApiError(message: String, response: Response<*>) {
        val errorDetails = "${response.code()} : ${response.message()}"
        Log.e(TAG, "$message - $errorDetails")
    }
    companion object {
        private const val TAG = "MainViewModel"
    }
}