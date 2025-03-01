package com.example.satellite.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.example.satellite.data.local.SatelliteDetailEntity
import com.example.satellite.data.model.Satellite
import com.example.satellite.domain.GetSatelliteDetailUseCase
import com.example.satellite.domain.GetSatelliteListUseCase
import com.example.satellite.domain.GetSatellitePositionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SatelliteViewModel @Inject constructor(
    private val getSatelliteListUseCase: GetSatelliteListUseCase,
    private val getSatelliteDetailUseCase: GetSatelliteDetailUseCase,
    private val getSatellitePositionsUseCase: GetSatellitePositionsUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _satellitePosition = MutableLiveData<Pair<Float, Float>>()
    val satellitePosition: LiveData<Pair<Float, Float>> = _satellitePosition

    private val _satelliteList = MutableLiveData<List<Satellite>>()
    val satelliteList: LiveData<List<Satellite>> = _satelliteList.distinctUntilChanged()

    private val _satelliteDetail = MutableLiveData<SatelliteDetailEntity?>()
    val satelliteDetail: LiveData<SatelliteDetailEntity?> = _satelliteDetail

    private val searchQuery = MutableStateFlow("")

    init {
        observeSearchQuery()
    }

    fun loadSatelliteList() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.postValue(true)
            delay(5000)
            Log.e("SatelliteViewModel", "Dara loading...")
            val satellites = getSatelliteListUseCase()

            Log.e("SatelliteViewModel", "Satellite list pulled: ${satellites.size} satellite")
            _satelliteList.postValue(satellites)
            _isLoading.postValue(false)
            Log.e("SatelliteViewModel", "Data loaded!")
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
    fun updateSearchQuery(query: String) {
        searchQuery.value = query
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            searchQuery
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isNotEmpty()) {
                    val fullList = getSatelliteListUseCase()
                    val filteredList = if (query.isEmpty()) {
                        fullList
                    } else {
                        fullList.filter { it.name.contains(query, ignoreCase = true) }
                    }
                    _satelliteList.postValue(filteredList)
                    }
                }
        }
    }
}
