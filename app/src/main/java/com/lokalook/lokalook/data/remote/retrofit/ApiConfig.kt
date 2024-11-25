package com.lokalook.lokalook.data.remote.retrofit

import com.lokalook.lokalook.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton class for configuring Retrofit and ApiService.
 */
object ApiConfig {

    /**
     * Returns an instance of [ApiService] ready for API communication.
     */
    fun getApiService(): ApiService {
        val client = createOkHttpClient()
        val retrofit = createRetrofit(client)
        return retrofit.create(ApiService::class.java)
    }

    /**
     * Creates and configures an instance of [OkHttpClient].
     */
    private fun createOkHttpClient(): OkHttpClient {
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

    /**
     * Creates and configures an instance of [Retrofit].
     */
    private fun createRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(getBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    /**
     * Dynamically fetches the base URL based on the environment.
     */
    private fun getBaseUrl(): String {
        return if (BuildConfig.BASE_URL.isNotBlank()) {
            BuildConfig.BASE_URL
        } else {
            "https://event-api.dicoding.dev/" // Fallback default URL
        }
    }
}