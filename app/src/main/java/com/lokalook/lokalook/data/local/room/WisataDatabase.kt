package com.lokalook.lokalook.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lokalook.lokalook.data.local.entity.WisataEntity

@Database(entities = [WisataEntity::class], version = 1, exportSchema = false)
abstract class WisataDatabase : RoomDatabase() {
    abstract fun wisataDao(): WisataDao

    companion object {
        @Volatile
        private var INSTANCE: WisataDatabase? = null

        fun getInstance(context: Context): WisataDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WisataDatabase::class.java,
                    "wisata_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}