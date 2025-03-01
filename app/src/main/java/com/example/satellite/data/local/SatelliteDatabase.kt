package com.example.satellite.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [SatelliteDetailEntity::class], version = 1, exportSchema = false)
abstract class SatelliteDatabase : RoomDatabase() {
    abstract fun satelliteDetailDao(): SatelliteDetailDao

}