package com.example.satellite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.satellite.data.model.Satellite


import com.example.satellite.data.repository.SatelliteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SatelliteViewModel(private val repository: SatelliteRepository) : ViewModel() {

    private val _satelliteList = MutableLiveData<List<Satellite>>()
    val satelliteList: LiveData<List<Satellite>> = _satelliteList

    fun loadSatelliteList() {
        viewModelScope.launch(Dispatchers.IO) {
            val satellites = repository.getSatelliteList()
            _satelliteList.postValue(satellites)
        }
    }
}