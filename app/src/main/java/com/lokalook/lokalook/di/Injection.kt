package com.lokalook.lokalook.di

import android.content.Context
import com.lokalook.lokalook.data.local.room.MakananDatabase
import com.lokalook.lokalook.data.local.room.WisataDatabase
import com.lokalook.lokalook.data.remote.MakananRepository
import com.lokalook.lokalook.data.remote.WisataRepository
import com.lokalook.lokalook.data.remote.response.UserPreferencesManager
import com.lokalook.lokalook.data.remote.response.userDataStore
import com.lokalook.lokalook.data.remote.retrofit.ApiConfig
import com.lokalook.lokalook.data.remote.retrofit.ApiService

/**
 * Kelas Injection bertanggung jawab untuk menyediakan instansi yang dibutuhkan oleh aplikasi.
 */
object Injection {

    /**
     * Menyediakan instansi ApiService dengan Bearer token dari UserPreferencesManager.
     * @param context Konteks aplikasi.
     * @return Instansi ApiService.
     */
    private fun provideApiService(context: Context): ApiService {
        // Get an instance of UserPreferencesManager
        val userPreferencesManager = UserPreferencesManager.getInstance(context.userDataStore)

        // Pass the UserPreferencesManager to ApiConfig for token management
        return ApiConfig.getApiService(userPreferencesManager)
    }

    /**
     * Menyediakan instansi WisataRepository.
     * @param context Konteks aplikasi.
     * @return Instansi WisataRepository.
     */
    fun provideWisataRepository(context: Context): WisataRepository {
        val apiService = provideApiService(context) // Mendapatkan layanan API dengan Bearer token
        val database = WisataDatabase.getInstance(context) // Mendapatkan instance basis data
        val dao = database.wisataDao() // Mendapatkan DAO untuk akses data
//        return WisataRepository.getInstance(apiService, dao)
    // Mengembalikan instansi WisataRepository
        return TODO("Provide the return value")
    }


    /**
     * Menyediakan instansi MakananRepository.
     * @param context Konteks aplikasi.
     * @return Instansi MakananRepository.
     */
    fun provideMakananRepository(context: Context): MakananRepository {
        val apiService = provideApiService(context) // Mendapatkan layanan API dengan Bearer token
        val database = MakananDatabase.getInstance(context) // Mendapatkan instance basis data
        val dao = database.makananDao() // Mendapatkan DAO untuk akses data
        //return MakananRepository.getInstance(apiService, dao) // Mengembalikan instansi MakananRepository
        return TODO("Provide the return value")
    }
}