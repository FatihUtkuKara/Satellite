package com.example.satellite.domain


import android.util.Log
import com.example.satellite.data.model.Satellite
import com.example.satellite.data.repository.SatelliteRepository
import javax.inject.Inject

class GetSatelliteListUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {
    suspend operator fun invoke(): List<Satellite> {
        Log.e("GetSatelliteListUseCase", "Data pulling...")
        val data = repository.getSatelliteList()
        Log.e("GetSatelliteListUseCase", "Data pulled : ${data.size}")
        return data
    }
}