package com.example.satellite.data.repository

import com.example.satellite.data.local.SatelliteDetailDao
import com.example.satellite.data.local.SatelliteDetailEntity
import javax.inject.Inject

class LocalSatelliteRepository @Inject constructor(private val satelliteDao: SatelliteDetailDao) {

    fun getSatelliteDetail(id: Int): SatelliteDetailEntity? {
        return satelliteDao.getSatelliteDetailById(id)
    }

    fun saveSatelliteDetail(detail: SatelliteDetailEntity) {
        satelliteDao.insertSatelliteDetail(detail)
    }
}