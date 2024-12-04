package com.lokalook.lokalook.data.remote.retrofit

import com.lokalook.lokalook.data.remote.response.LoginResponse
import com.lokalook.lokalook.data.remote.response.RegisterResponse
import com.lokalook.lokalook.data.remote.response.TokenResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST("generate-token")
    fun generateToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Call<TokenResponse>

    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Header("Authorization") token: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun registerUser(
        @Header("Authorization") token: String,
        @Field("nama_lengkap") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("username") username: String,
        @Field("alamat") address: String,
        @Field("hasil_personalisasi") personalizationResult: String,
        @Field("image_pp") profileImage: String
    ): Call<RegisterResponse>
}