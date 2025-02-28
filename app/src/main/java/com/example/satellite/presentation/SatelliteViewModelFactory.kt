package com.example.satellite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.satellite.data.repository.SatelliteRepository

class SatelliteViewModelFactory(private val repository: SatelliteRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SatelliteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SatelliteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}