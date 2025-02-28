package com.example.satellite.domain


import com.example.satellite.data.model.Satellite
import com.example.satellite.data.repository.SatelliteRepository
import javax.inject.Inject

class GetSatelliteListUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {
    suspend operator fun invoke(): List<Satellite> {
        return repository.getSatelliteList()
    }
}