package com.lokalook.lokalook.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.lokalook.lokalook.data.local.entity.WisataEntity
import com.lokalook.lokalook.data.local.room.WisataDao
import com.lokalook.lokalook.data.remote.retrofit.ApiService
import com.lokalook.lokalook.utils.Result

class WisataRepository(
//    private val apiService: ApiService,
//    private val wisataDao: WisataDao
//) {
//
//    fun getWisata(bearerToken: String): LiveData<Result<List<WisataEntity>>> = liveData {
//        emit(Result.Loading) // Emit loading state
//
//        try {
//            // Fetch data from API
//            val response = apiService.getWisata(bearerToken).execute()
//            if (response.isSuccessful) {
//                val wisataList = response.body()?.wisata?.map { wisata ->
//                    WisataEntity(
//                        id = wisata.id,
//                        nama_wisata = wisata.nama_wisata,
//                        alamat = wisata.alamat,
//                        deskripsi = wisata.deskripsi,
//                        image = wisata.image,
//                        harga = wisata.harga,
//                        jam_operasional = wisata.jam_operasional,
//                        no_wa = wisata.no_wa,
//                        kategori = wisata.kategori,
//                        isFavorite = false
//                    )
//                }
//                wisataDao.insertWisata(wisataList ?: emptyList()) // Save to the local database
//            } else {
//                emit(Result.Error(response.message()))
//            }
//        } catch (e: Exception) {
//            Log.e("WisataRepository", "Error fetching wisata: ${e.message}")
//            emit(Result.Error(e.message ?: "Unknown Error"))
//        }
//
//        // Fetch data from the local database
//        val localData: LiveData<Result<List<WisataEntity>>> =
//            wisataDao.getAllWisata().map { wisataList ->
//                Result.Success(wisataList)
//            }
//        emitSource(localData)
//    }
//
//    companion object {
//        @Volatile
//        private var instance: WisataRepository? = null
//
//        fun getInstance(
//            apiService: ApiService,
//            wisataDao: WisataDao
//        ): WisataRepository = instance ?: synchronized(this) {
//            instance ?: WisataRepository(apiService, wisataDao).also { instance = it }
//        }
//    }
)
//}