package com.lokalook.lokalook.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import okhttp3.*
import java.io.IOException

class ResultViewModel : ViewModel() {

    private val _resultData = MutableLiveData<JsonObject?>()
    val resultData: LiveData<JsonObject?> = _resultData

    fun fetchResultData(budget: String, jumlahOrang: String) {
        viewModelScope.launch {
            val client = OkHttpClient()
            val url = "https://api-smartbudget.et.r.appspot.com/rekomendasi?budget=$budget&num_people=$jumlahOrang"
            val request = Request.Builder()
                .url(url)
                .build()

            try {
                val response = withContext(Dispatchers.IO) { client.newCall(request).execute() }
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val jsonObject = responseBody?.let { Json.decodeFromString<JsonObject>(it) }
                    _resultData.value = jsonObject
                } else {
                    _resultData.value = null // Atau handle error di sini
                    // Contoh penanganan error:
                    // Log.e("ResultViewModel", "API request failed: ${response.code}")
                }
            } catch (e: IOException) {
                _resultData.value = null // Atau handle error di sini
                // Contoh penanganan error:
                // Log.e("ResultViewModel", "Network error: ${e.message}")
            }
        }
    }
}