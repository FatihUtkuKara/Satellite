package com.example.satellite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.satellite.presentation.SatelliteViewModel
import android.widget.TextView
import androidx.activity.viewModels
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

        viewModel.satelliteDetail.observe(this) { detail ->
            detail?.let {
                nameTextView.text = "ID: $satelliteId"
                costTextView.text = "Launch Cost: $${it.costPerLaunch}"
                heightTextView.text = "Height: ${it.height} m"
                massTextView.text = "Mass: ${it.mass} kg"
                firstFlightTextView.text = "First Flight: ${it.firstFlight}"
            }
        }

        viewModel.satellitePosition.observe(this) { position ->
            positionTextView.text = "Position: (${position.first}, ${position.second})"
        }
    }
}
