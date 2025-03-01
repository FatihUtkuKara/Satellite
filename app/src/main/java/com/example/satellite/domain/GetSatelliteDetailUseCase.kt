package com.example.satellite.domain

import com.example.satellite.data.local.SatelliteDetailEntity
import com.example.satellite.data.repository.SatelliteRepository

import javax.inject.Inject

class GetSatelliteDetailUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {
    suspend operator fun invoke(id: Int): SatelliteDetailEntity? {
        return repository.getSatelliteDetail(id)
    }

}