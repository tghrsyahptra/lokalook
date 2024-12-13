package com.lokalook.lokalook.data.remote.retrofit

import com.lokalook.lokalook.data.remote.response.EventResponse
import com.lokalook.lokalook.data.remote.response.LoginResponse
import com.lokalook.lokalook.data.remote.response.RecommendationResponse
import com.lokalook.lokalook.data.remote.response.RegisterResponse
import com.lokalook.lokalook.data.remote.response.TokenResponse
import com.lokalook.lokalook.data.remote.response.WisataResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/**
 * Antarmuka gabungan untuk layanan API.
 * Menggabungkan endpoint untuk Event API dan Story API.
 */
interface ApiService {

    // --- Event API ---

    /**
     * Mengambil daftar acara berdasarkan parameter yang ditentukan.
     *
     * @param active Menentukan status acara: 1 untuk acara yang akan datang, 0 untuk acara selesai, -1 untuk semua acara.
     * @param query Kata kunci pencarian untuk memfilter acara (opsional).
     * @param limit Jumlah maksimum acara yang akan diambil (default: 40).
     * @return Respons yang berisi daftar acara.
     */
    @GET("events")
    suspend fun getEvents(
        @Query("active") active: Int = 1,
        @Query("q") query: String? = null,
        @Query("limit") limit: Int = 40
    ): EventResponse

    /**
     * Mendaftarkan pengguna baru ke aplikasi.
     *
     * @param name Nama pengguna.
     * @param email Alamat email pengguna.
     * @param password Kata sandi pengguna.
     * @return Objek [Call] yang mengembalikan [RegisterResponse].
     */
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

    /**
     * Melakukan proses login pengguna.
     *
     * @param email Alamat email pengguna.
     * @param password Kata sandi pengguna.
     * @return Objek [Call] yang mengembalikan [LoginResponse].
     */
    @FormUrlEncoded
    @POST("login")
    fun loginUser(
        @Header("Authorization") token: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("generate-token")
    fun generateToken(
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Call<TokenResponse>

    @GET("get-wisata")
    fun getWisata(
        @Header("Authorization") bearerToken: String
    ): Call<WisataResponse>

    @GET("rekomendasi")
    suspend fun getRecommendation(
        @Query("budget") budget: Int,
        @Query("num_people") numPeople: Int
    ): Response<RecommendationResponse>


}