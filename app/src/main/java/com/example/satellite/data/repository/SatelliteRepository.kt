package com.example.satellite.data.repository


import android.content.Context
import android.util.Log
import com.example.satellite.data.local.SatelliteDetailDao
import com.example.satellite.data.local.SatelliteDetailEntity
import com.example.satellite.data.model.PositionData
import com.example.satellite.data.model.PositionResponse
import com.example.satellite.data.model.Satellite
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class responsible for fetching satellite data from JSON files.
 */
@Singleton
class SatelliteRepository @Inject constructor(
    private val satelliteDetailDao: SatelliteDetailDao,
    @ApplicationContext private val context: Context
) {

    /**
     * Reads satellite list from satellites.json*
     */
    fun getSatelliteList(): List<Satellite> {
        return try {
            val json = readJsonFromAssets("satellites.json")
            Gson().fromJson(json, object : TypeToken<List<Satellite>>() {}.type)
        } catch (e: Exception) {
            Log.e("SatelliteRepository", "Error reading satellite list: ${e.message}")
            emptyList()
        }
    }

    suspend fun getSatelliteDetail(id: Int): SatelliteDetailEntity? {
        val cachedDetail = satelliteDetailDao.getSatelliteDetailById(id)
        if (cachedDetail != null) return cachedDetail
        val json = readJsonFromAssets("satellite-detail.json") ?: return null
        val details = Gson().fromJson(json, Array<SatelliteDetailEntity>::class.java).toList()
        val detail = details.find { it.id == id }
        Log.i("JSON_DATA", json)
        detail?.let {
            satelliteDetailDao.insertSatelliteDetail(it)
        }

        return detail
    }


    private fun readJsonFromAssets(fileName: String): String {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    fun getSatellitePositions(id: Int): List<PositionData> {
        val json = readJsonFromAssets("positions.json") ?: return emptyList()
        val positionData = Gson().fromJson(json, PositionResponse::class.java)
        return positionData.list.find { it.id == id.toString() }?.positions ?: emptyList()
    }

}
