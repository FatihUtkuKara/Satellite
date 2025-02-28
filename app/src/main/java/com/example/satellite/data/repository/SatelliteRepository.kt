package com.example.satellite.data.repository

import android.content.Context
import android.util.Log
import com.example.satellite.data.local.SatelliteDetailEntity
import com.google.gson.Gson
import java.io.IOException
import javax.inject.Inject

class SatelliteRepository @Inject constructor(
    private val localRepository: LocalSatelliteRepository,
    private val context: Context
) {
    fun getSatelliteDetail(id: Int): SatelliteDetailEntity? {

        val cachedDetail = localRepository.getSatelliteDetail(id)
        if (cachedDetail != null) return cachedDetail

        val json = try {
            readJsonFromAssets(context, "satellite-detail.json")
        } catch (e: Exception) {
            Log.e("SatelliteRepository", "Error reading JSON file: ${e.message}")
            return null
        }
        val details = Gson().fromJson(json, Array<SatelliteDetailEntity>::class.java).toList()
        val detail = details.find { it.id == id }

        detail?.let { localRepository.saveSatelliteDetail(it) }
        return detail
    }

    fun readJsonFromAssets(context: Context, fileName: String): String {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }

    }
}

