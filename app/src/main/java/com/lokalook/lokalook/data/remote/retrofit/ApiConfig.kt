package com.lokalook.lokalook.data.remote.retrofit

import com.lokalook.lokalook.BuildConfig
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    // API service with token authentication
    fun getApiService(userPreferencesManager: UserPreferencesManager): ApiService {
        val client = createOkHttpClient(userPreferencesManager)
        val retrofit = createRetrofit(client)
        return retrofit.create(ApiService::class.java)
    }

    // Fallback API service without authentication (if needed)
    fun getApiService(): ApiService {
        val client = createOkHttpClientWithoutAuth()
        val retrofit = createRetrofit(client)
        return retrofit.create(ApiService::class.java)
    }

    private fun createOkHttpClient(userPreferencesManager: UserPreferencesManager): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        // Add token to requests via Interceptor
        val authInterceptor = Interceptor { chain ->
            val token = userPreferencesManager.getUserToken() // Retrieve the token
            val request = if (token.isNotEmpty()) {
                chain.request().newBuilder()
                    .addHeader("Authorization", token)
                    .build()
            } else {
                chain.request()
            }
            chain.proceed(request)
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(authInterceptor)
            .build()
    }

    private fun createOkHttpClientWithoutAuth(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    private fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}