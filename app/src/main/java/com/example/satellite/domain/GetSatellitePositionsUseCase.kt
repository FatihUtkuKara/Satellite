package com.example.satellite.domain


import com.example.satellite.data.model.PositionData
import com.example.satellite.data.repository.SatelliteRepository
import javax.inject.Inject

class GetSatellitePositionsUseCase @Inject constructor(
    private val repository: SatelliteRepository
) {
    operator fun invoke(id: Int): List<PositionData> {
        return repository.getSatellitePositions(id)
    }
}