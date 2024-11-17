package com.lokalook.lokalook.di

import android.content.Context
import com.lokalook.lokalook.data.local.room.EventDatabase
import com.lokalook.lokalook.data.remote.EventRepository
import com.lokalook.lokalook.data.remote.retrofit.ApiConfig

/**
 * Kelas Injection bertanggung jawab untuk menyediakan instansi yang dibutuhkan oleh aplikasi.
 */
object Injection {

    /**
     * Menyediakan instansi EventRepository.
     * @param context Konteks aplikasi.
     * @return Instansi EventRepository.
     */
    fun provideRepository(context: Context): EventRepository {
        val apiService = ApiConfig.getApiService() // Mendapatkan layanan API
        val database = EventDatabase.getInstance(context) // Mendapatkan instance basis data
        val dao = database.eventDao() // Mendapatkan DAO untuk akses data
        return EventRepository.getInstance(apiService, dao) // Mengembalikan instansi EventRepository
    }
}