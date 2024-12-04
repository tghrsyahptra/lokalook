package com.lokalook.lokalook.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.lokalook.lokalook.data.local.entity.MakananEntity

@Database(entities = [MakananEntity::class], version = 1, exportSchema = false)
abstract class MakananDatabase : RoomDatabase() {
    abstract fun makananDao(): MakananDao

    companion object {
        @Volatile
        private var INSTANCE: MakananDatabase? = null

        fun getInstance(context: Context): MakananDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MakananDatabase::class.java,
                    "makanan_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}