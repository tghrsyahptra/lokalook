package com.lokalook.lokalook.ui.login

import androidx.lifecycle.*
import com.lokalook.lokalook.data.remote.retrofit.ApiConfig
import com.lokalook.lokalook.data.remote.response.LoginResponse
import com.lokalook.lokalook.data.remote.response.LoginResult
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val userPreference: UserPreferencesManager) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = MutableLiveData<String>()
    val message = MutableLiveData<String>()

    val loginResult = MutableLiveData<LoginResponse>()

    fun getUser(): LiveData<LoginResult> {
        return userPreference.getUser().asLiveData()
    }

    fun saveUser(userName: String, userId: String, userToken: String) {
        viewModelScope.launch {
            userPreference.saveUser(userName, userId, userToken)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            userPreference.signOut()
        }
    }

    fun login(email: String, password: String) {
        _isLoading.value = true  // Tampilkan ProgressBar
        val client = ApiConfig.getApiService().loginUser(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false  // Sembunyikan ProgressBar setelah selesai
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        loginResult.postValue(loginResponse)
                        message.postValue("Login successful!")
                    }
                } else {
                    handleErrorResponse(response)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false  // Sembunyikan ProgressBar jika gagal
                error.postValue("Failed to connect: ${t.message}")
            }
        })
    }

    private fun handleErrorResponse(response: Response<LoginResponse>) {
        when (response.code()) {
            400 -> error.postValue("Bad request: Invalid input")
            401 -> error.postValue("Unauthorized: Invalid credentials")
            else -> error.postValue("Error ${response.code()}: ${response.message()}")
        }
    }
}