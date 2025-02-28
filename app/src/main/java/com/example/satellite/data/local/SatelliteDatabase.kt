package com.example.satellite.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SatelliteDetailEntity::class], version = 1, exportSchema = false)
abstract class SatelliteDatabase : RoomDatabase() {

    abstract fun satelliteDetailDao(): SatelliteDetailDao

    companion object {
        @Volatile
        private var INSTANCE: SatelliteDatabase? = null

        fun getDatabase(context: Context): SatelliteDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SatelliteDatabase::class.java,
                    "satellite_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}