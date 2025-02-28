package com.example.satellite.data.repository


import android.content.Context
import com.example.satellite.data.model.Satellite
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


class SatelliteRepository private constructor(context: Context) {

    private val appContext = context.applicationContext

    fun getSatelliteList(): List<Satellite> {
        val json = readJsonFromAssets(appContext, "satellites.json") ?: return emptyList()
        return Gson().fromJson(json, object : TypeToken<List<Satellite>>() {}.type)
    }

    companion object {
        @Volatile private var instance: SatelliteRepository? = null

        fun getInstance(context: Context): SatelliteRepository {
            return instance ?: synchronized(this) {
                instance ?: SatelliteRepository(context).also { instance = it }
            }
        }
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
