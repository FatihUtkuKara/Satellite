package com.example.satellite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satellite.presentation.SatelliteViewModel
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.satellite.data.local.SatelliteDetailEntity
import com.example.satellite.databinding.ActivitySatelliteDetailBinding
import com.example.satellite.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatelliteDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySatelliteDetailBinding
    private val viewModel: SatelliteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySatelliteDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val satelliteId = intent.getIntExtra("satellite_id", -1)
        val satelliteName = intent.getStringExtra("satellite_name") ?: "Unknown"
        if (satelliteId != -1) {
            viewModel.loadSatelliteDetail(satelliteId)
            viewModel.startPositionUpdates(satelliteId)
        }

        viewModel.satelliteDetailResource.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    updateDetailUI(resource.data, satelliteName)
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                }

            }
        }

        viewModel.satellitePosition.observe(this) { position ->
            binding.satellitePosition.text = "Position: (${position.first}, ${position.second})"
        }
    }

    private fun updateDetailUI(detail: SatelliteDetailEntity?,satelliteName : String) {
        if (detail == null) return

        binding.satelliteName.text = satelliteName
        binding.satelliteCost.text = "Cost per launch: ${detail.costPerLaunch}$"
        binding.satelliteFirstFlight.text = "First flight: ${detail.firstFlight}"
        binding.satelliteHeight.text = "Height: ${detail.height} m"
        binding.satelliteMass.text = "Mass: ${detail.mass} kg"
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}
