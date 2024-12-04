package com.lokalook.lokalook.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokalook.lokalook.data.remote.retrofit.ApiConfig
import com.lokalook.lokalook.data.remote.response.RegisterResponse
import com.lokalook.lokalook.data.remote.response.TokenResponse
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreferencesManager) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = MutableLiveData<String>()
    val message = MutableLiveData<String>()

    private var token: String? = null

    /**
     * Generates a token and registers the user.
     * @param clientId Client ID for token generation.
     * @param clientSecret Client secret for token generation.
     * @param name Full name of the user.
     * @param email Email address of the user.
     * @param password Password for the account.
     * @param username Username for the account.
     * @param address Address of the user.
     * @param personalizationResult Personalization result data (Default value provided).
     * @param profileImage URL of the user's profile image (Default value provided).
     */
    fun generateTokenAndRegister(
        clientId: String,
        clientSecret: String,
        name: String,
        email: String,
        password: String,
        username: String,
        address: String,
        personalizationResult: String = "personalized result", // Default Value
        profileImage: String = "https://example.com/path/to/image.jpg" // Default Value
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().generateToken(clientId, clientSecret)
        client.enqueue(object : Callback<TokenResponse> {
            override fun onResponse(call: Call<TokenResponse>, response: Response<TokenResponse>) {
                if (response.isSuccessful) {
                    token = response.body()?.token
                    if (token != null) {
                        registerUser(
                            token!!,
                            name,
                            email,
                            password,
                            username,
                            address,
                            personalizationResult,
                            profileImage
                        )
                    } else {
                        _isLoading.value = false
                        error.postValue("Failed to generate token.")
                    }
                } else {
                    _isLoading.value = false
                    error.postValue("Token generation failed: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TokenResponse>, t: Throwable) {
                _isLoading.value = false
                error.postValue("Failed to connect: ${t.message}")
            }
        })
    }

    /**
     * Registers the user using the generated token.
     * @param token Bearer token for authorization.
     */
    private fun registerUser(
        token: String,
        name: String,
        email: String,
        password: String,
        username: String,
        address: String,
        personalizationResult: String,
        profileImage: String
    ) {
        val client = ApiConfig.getApiService().registerUser(
            "Bearer $token",
            name,
            email,
            password,
            username,
            address,
            personalizationResult,
            profileImage
        )

        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                when (response.code()) {
                    201 -> message.postValue("201") // Success
                    400 -> error.postValue("400") // Bad Request
                    else -> error.postValue("Error ${response.code()}: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                error.postValue("Failed to connect: ${t.message}")
            }
        })
    }
}