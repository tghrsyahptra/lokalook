package com.lokalook.lokalook.ui.login

import androidx.lifecycle.*
import com.lokalook.lokalook.data.remote.response.LoginResponse
import com.lokalook.lokalook.data.remote.response.LoginResult
import com.lokalook.lokalook.data.remote.response.TokenResponse
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import com.lokalook.lokalook.data.remote.retrofit.ApiConfig
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

    private var token: String? = null

    fun getUser(): LiveData<LoginResult> {
        return userPreference.getUser().asLiveData()
    }

    fun saveUser(userName: String, userId: String, userToken: String) {
        viewModelScope.launch {
            userPreference.saveUser(userName, userId, userToken)
        }
    }

    fun generateToken(clientId: String, clientSecret: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().generateToken(clientId, clientSecret)
        client.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    token = response.body()?.token
                    login(token, email, password)
                } else {
                    _isLoading.value = false
                    error.postValue("Failed to generate token: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                _isLoading.value = false
                error.postValue("Failed to connect: ${t.message}")
            }
        })
    }

    private fun login(token: String?, email: String, password: String) {
        if (token == null) {
            error.postValue("Token is missing")
            _isLoading.value = false
            return
        }

        val client = ApiConfig.getApiService().loginUser("Bearer $token", email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    response.body()?.let { loginResponse ->
                        loginResult.postValue(loginResponse)
                        message.postValue("Login successful!")
                        saveUser(
                            loginResponse.loginResult.name,
                            loginResponse.loginResult.userId,
                            token
                        )
                    }
                } else {
                    handleErrorResponse(response)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
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