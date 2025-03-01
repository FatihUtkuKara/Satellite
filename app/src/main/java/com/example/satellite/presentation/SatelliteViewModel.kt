package com.example.satellite.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.satellite.data.local.SatelliteDetailEntity
import com.example.satellite.data.model.Satellite
import com.example.satellite.domain.GetSatelliteDetailUseCase
import com.example.satellite.domain.GetSatelliteListUseCase
import com.example.satellite.domain.GetSatellitePositionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteViewModel @Inject constructor(
    private val getSatelliteListUseCase: GetSatelliteListUseCase,
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val getSatellitePositionsUseCase: GetSatellitePositionsUseCase
) : ViewModel() {

    private val _satellitePosition = MutableLiveData<Pair<Float, Float>>()
    val satellitePosition: LiveData<Pair<Float, Float>> = _satellitePosition

    private val _satelliteList = MutableLiveData<List<Satellite>>()
    val satelliteList: LiveData<List<Satellite>> = _satelliteList

    private val _satelliteDetail = MutableLiveData<SatelliteDetailEntity?>()
    val satelliteDetail: LiveData<SatelliteDetailEntity?> = _satelliteDetail

    fun loadSatelliteList() {
        viewModelScope.launch(Dispatchers.IO) {
            val satellites = getSatelliteListUseCase()
            _satelliteList.postValue(satellites)
        }
    }

    fun loadSatelliteDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val detail = getSatelliteDetailUseCase(id)
            _satelliteDetail.postValue(detail)
        }
    }

    fun startPositionUpdates(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val positions = getSatellitePositionsUseCase(id)
            if (positions.isNotEmpty()) {
                _satellitePosition.postValue(Pair(positions[0].posX, positions[0].posY))


                for (i in 1 until positions.size) {
                    delay(3000)
                    _satellitePosition.postValue(Pair(positions[i].posX, positions[i].posY))
                }
            }
        }

    }
}
