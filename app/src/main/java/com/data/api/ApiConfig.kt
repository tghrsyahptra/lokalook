package com.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiService {
        val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                // Jika Anda ingin menambahkan header Content-Type hanya untuk file upload, Anda bisa cek tipe request
                val request = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json") // atau multipart/form-data jika upload file
                    .build()
                chain.proceed(request)
            }
            .addInterceptor(loggingInterceptor) // Add logging interceptor
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://lokalook-cp.et.r.appspot.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client) // Gunakan client yang sudah diset
            .build()

        return retrofit.create(ApiService::class.java)
    }
}
