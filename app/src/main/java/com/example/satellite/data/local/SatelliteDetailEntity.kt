package com.example.satellite.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "satellite_details")
data class SatelliteDetailEntity(
    @PrimaryKey val id: Int,
    val costPerLaunch: Int,
    val firstFlight: String,
    val height: Int,
    val mass: Int
)
