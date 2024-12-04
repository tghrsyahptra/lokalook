package com.lokalook.lokalook.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lokalook.lokalook.data.local.entity.WisataEntity

@Dao
interface WisataDao {

    // Query to get all favorite wisata
    @Query("SELECT * FROM wisata WHERE isFavorite = 1")
    fun getFavoriteWisata(): LiveData<List<WisataEntity>>

    // Query to search favorite wisata by keyword
    @Query("""
    SELECT * FROM wisata 
    WHERE isFavorite = 1
    AND (
        nama_wisata LIKE '%' || :query || '%' OR 
        alamat LIKE '%' || :query || '%' OR 
        deskripsi LIKE '%' || :query || '%' OR 
        kategori LIKE '%' || :query || '%'
    )
    """)
    fun searchFavoriteWisata(query: String): LiveData<List<WisataEntity>>

    // Insert or replace wisata items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWisata(wisataList: List<WisataEntity>)

    // Update a specific wisata item
    @Update
    suspend fun updateWisata(wisata: WisataEntity)

    // Delete all non-favorite wisata
    @Query("DELETE FROM wisata WHERE isFavorite = 0")
    suspend fun deleteAllNonFavorite()

    // Check if a specific wisata is marked as favorite
    @Query("SELECT EXISTS(SELECT * FROM wisata WHERE nama_wisata = :namaWisata AND isFavorite = 1)")
    suspend fun isWisataFavorite(namaWisata: String): Boolean

    // Query to get all wisata
    @Query("SELECT * FROM wisata")
    fun getAllWisata(): LiveData<List<WisataEntity>>

    // Query to search all wisata by keyword
    @Query("""
    SELECT * FROM wisata
    WHERE 
        nama_wisata LIKE '%' || :query || '%' OR 
        alamat LIKE '%' || :query || '%' OR 
        deskripsi LIKE '%' || :query || '%' OR 
        kategori LIKE '%' || :query || '%'
    """)
    fun searchAllWisata(query: String): LiveData<List<WisataEntity>>
}