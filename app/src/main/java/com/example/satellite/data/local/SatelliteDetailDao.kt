package com.example.satellite.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SatelliteDetailDao {
    @Query("SELECT * FROM satellite_details WHERE id = :id LIMIT 1")
    suspend fun getSatelliteDetailById(id: Int): SatelliteDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSatelliteDetail(detail: SatelliteDetailEntity)
}