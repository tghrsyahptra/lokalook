package com.lokalook.lokalook.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lokalook.lokalook.data.remote.retrofit.ApiConfig
import com.lokalook.lokalook.data.remote.response.RegisterResponse
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel(private val pref: UserPreferencesManager): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    val error = MutableLiveData("")
    val message = MutableLiveData("")
    private val tag = RegisterViewModel::class.java.simpleName

    fun register(name:String, email:String, password:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().registerUser(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                when(response.code()) {
                    400 -> error.postValue("400")
                    201 -> message.postValue("201")
                    else -> error.postValue("ERROR ${response.code()} : ${response.errorBody()}")
                }
                _isLoading.value = false
            }
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = true
                Log.e(tag, "onFailure Call: ${t.message}")
                error.postValue(t.message)
            }
        })
    }
}