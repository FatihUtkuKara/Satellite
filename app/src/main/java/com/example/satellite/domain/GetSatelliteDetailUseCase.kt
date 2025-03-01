package com.example.satellite.domain

import com.example.satellite.data.local.SatelliteDetailEntity
import com.example.satellite.data.repository.SatelliteRepository
import com.example.satellite.util.Resource

import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {
    suspend operator fun invoke(id: Int): Resource<SatelliteDetailEntity?> {
        return try {
            val detail = repository.getSatelliteDetail(id)
            if (detail != null) {
                Resource.Success(detail)
            } else {
                Resource.Error("Satellite detail not found")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to load satellite detail", e)
        }
    }

}