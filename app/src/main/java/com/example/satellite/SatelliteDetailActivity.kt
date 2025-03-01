package com.example.satellite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satellite.presentation.SatelliteViewModel
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.satellite.data.local.SatelliteDetailEntity
import com.example.satellite.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SatelliteDetailActivity : AppCompatActivity() {

    private val viewModel: SatelliteViewModel by viewModels()
    private lateinit var nameTextView: TextView
    private lateinit var costTextView: TextView
    private lateinit var heightTextView: TextView
    private lateinit var massTextView: TextView
    private lateinit var firstFlightTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_satellite_detail)

        nameTextView = findViewById(R.id.satellite_name)
        costTextView = findViewById(R.id.satellite_cost)
        heightTextView = findViewById(R.id.satellite_height)
        massTextView = findViewById(R.id.satellite_mass)
        firstFlightTextView = findViewById(R.id.satellite_first_flight)
        val positionTextView: TextView = findViewById(R.id.satellite_position)

        val satelliteId = intent.getIntExtra("satellite_id", -1)
        if (satelliteId != -1) {
            viewModel.loadSatelliteDetail(satelliteId)
            viewModel.startPositionUpdates(satelliteId)
        }

        viewModel.satelliteDetailResource.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                }
                is Resource.Success -> {
                    updateDetailUI(resource.data)
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()
                }

            }
        }

        viewModel.satellitePosition.observe(this) { position ->
            positionTextView.text = "Position: (${position.first}, ${position.second})"
        }
    }

    private fun updateDetailUI(detail: SatelliteDetailEntity?) {
        if (detail == null) return

        costTextView.text = "Cost per launch: ${detail.costPerLaunch}$"
        firstFlightTextView.text = "First flight: ${detail.firstFlight}"
        heightTextView.text = "Height: ${detail.height} m"
        massTextView.text = "Mass: ${detail.mass} kg"
    }
}
