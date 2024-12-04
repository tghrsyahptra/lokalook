package com.lokalook.lokalook.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.lokalook.lokalook.data.local.entity.MakananEntity

@Dao
interface MakananDao {

    @Query("SELECT * FROM makanan")
    fun getAllMakanan(): LiveData<List<MakananEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMakanan(makananList: List<MakananEntity>?)

    @Query("DELETE FROM makanan")
    suspend fun deleteAllMakanan()
}