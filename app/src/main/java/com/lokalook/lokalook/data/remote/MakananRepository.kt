package com.lokalook.lokalook.data.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.lokalook.lokalook.data.local.entity.MakananEntity
import com.lokalook.lokalook.data.local.room.MakananDao
import com.lokalook.lokalook.data.remote.response.MakananResponse
import com.lokalook.lokalook.data.remote.retrofit.ApiService
import com.lokalook.lokalook.utils.Result

class MakananRepository(
    private val apiService: ApiService,
    private val makananDao: MakananDao
) {

//    fun getMakanan(bearerToken: String): LiveData<Result<List<MakananEntity>>> = liveData {
//        emit(Result.Loading) // Emit loading state
//
//        try {
//            // Fetch data from API
//            val response = apiService.getMakanan(bearerToken).execute()
//            if (response.isSuccessful) {
//                val makananList = response.body()?.makanan?.map { makanan ->
//                    MakananEntity(
//                        id = makanan.id,
//                        namaToko = makanan.namaToko,
//                        alamat = makanan.alamat,
//                        deskripsi = makanan.deskripsi,
//                        image = makanan.image,
//                        noWa = makanan.noWa,
//                        jamOperasional = makanan.jamOperasional,
//                        harga = makanan.harga
//                    )
//                }
//                makananDao.insertMakanan(makananList ?: emptyList()) // Save to local database
//            } else {
//                emit(Result.Error(response.message()))
//            }
//        } catch (e: Exception) {
//            Log.e("MakananRepository", "Error fetching makanan: ${e.message}")
//            emit(Result.Error(e.message ?: "Unknown Error"))
//        }
//
//        // Fetch data from the local database
//        val localData: LiveData<Result<List<MakananEntity>>> =
//            makananDao.getAllMakanan().map { makananList ->
//                Result.Success(makananList)
//            }
//        emitSource(localData)
//    }
//
//    companion object {
//        @Volatile
//        private var instance: MakananRepository? = null
//
//        fun getInstance(
//            apiService: ApiService,
//            makananDao: MakananDao
//        ): MakananRepository = instance ?: synchronized(this) {
//            instance ?: MakananRepository(apiService, makananDao).also { instance = it }
//        }
//    }
}