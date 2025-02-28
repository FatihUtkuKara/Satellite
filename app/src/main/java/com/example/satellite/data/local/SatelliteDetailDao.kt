package com.example.satellite.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SatelliteDetailDao {
    @Query("SELECT * FROM satellite_details WHERE id = :id LIMIT 1")
    fun getSatelliteDetailById(id: Int): SatelliteDetailEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSatelliteDetail(detail: SatelliteDetailEntity)
}