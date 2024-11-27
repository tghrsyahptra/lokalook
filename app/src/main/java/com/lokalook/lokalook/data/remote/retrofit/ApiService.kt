package com.lokalook.lokalook.data.remote.retrofit

import com.lokalook.lokalook.data.remote.response.EventResponse
import com.lokalook.lokalook.data.remote.response.LoginResponse
import com.lokalook.lokalook.data.remote.response.RegisterResponse
import retrofit2.Call
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
        @Field("name") name: String?,
        @Field("email") email: String?,
        @Field("password") password: String?
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
        @Field("email") email: String?,
        @Field("password") password: String?
    ): Call<LoginResponse>
}