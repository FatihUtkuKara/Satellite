package com.example.satellite.domain


import com.example.satellite.data.model.Satellite
import com.example.satellite.data.repository.SatelliteRepository
import com.example.satellite.util.Resource
import javax.inject.Inject

class GetSatelliteListUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {
    suspend operator fun invoke(): Resource<List<Satellite>> {
        return try {
            val satellites = repository.getSatelliteList()
            if (satellites.isNotEmpty()) {
                Resource.Success(satellites)
            } else {
                Resource.Error("Satellite list is empty")
            }
        } catch (e: Exception) {
            Resource.Error("Failed to load satellite list", e)
        }
    }
}