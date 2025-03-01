package com.example.satellite

import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.satellite.data.model.Satellite
import com.example.satellite.domain.GetSatelliteDetailUseCase
import com.example.satellite.domain.GetSatelliteListUseCase
import com.example.satellite.domain.GetSatellitePositionsUseCase
import com.example.satellite.presentation.SatelliteViewModel
import com.example.satellite.util.Resource
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows


@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
class SatelliteViewModelTest {

    // Objects to be mocked
    private lateinit var getSatelliteListUseCase: GetSatelliteListUseCase
    private lateinit var getSatelliteDetailUseCase: GetSatelliteDetailUseCase
    private lateinit var getSatellitePositionsUseCase: GetSatellitePositionsUseCase
    private lateinit var viewModel: SatelliteViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        getSatelliteListUseCase = mockk()
        getSatelliteDetailUseCase = mockk()
        getSatellitePositionsUseCase = mockk()
        viewModel = SatelliteViewModel(getSatelliteListUseCase, getSatelliteDetailUseCase, getSatellitePositionsUseCase)
    }

    @Test
    fun `loadSatelliteList() should post success state when use case returns data`() = runTest {
        // Mocked data
        val mockSatellites = listOf(Satellite(1,true,"Starship-1"))

        // Determine the value that UseCase will return
        coEvery { getSatelliteListUseCase() } returns Resource.Success(mockSatellites)

        // Call ViewModel function
        viewModel.loadSatelliteList()

        advanceUntilIdle()

        // Test whether the result is successful or not
        val result = viewModel.satelliteListResource.getOrAwaitValue()
        assert(result is Resource.Success<*>)
        assert((result as Resource.Success<*>).data == mockSatellites)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
