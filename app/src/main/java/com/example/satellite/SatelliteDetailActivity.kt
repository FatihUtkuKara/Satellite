package com.example.satellite

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.satellite.presentation.SatelliteViewModel
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.bold
import com.example.satellite.data.local.SatelliteDetailEntity
import com.example.satellite.databinding.ActivitySatelliteDetailBinding
import com.example.satellite.util.Resource
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity to display satellite details.
 */
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

        setupObservers()
        if (satelliteId != -1) {
            fetchSatelliteData(satelliteId, satelliteName)
        }
    }

    /**
     * Observes ViewModel live data and updates UI accordingly.
     */
    private fun setupObservers() {
        viewModel.satelliteDetailResource.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    Log.i("SatelliteDetailActivity", "Loading satellite details...")
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
            binding.satellitePosition.text = formatText("Last Position: ", "(${position.first}, ${position.second})")
        }
    }

    /**
     * Fetches satellite data and starts position updates.
     */
    private fun fetchSatelliteData(id: Int, name: String) {
        viewModel.loadSatelliteDetail(id)
        viewModel.startPositionUpdates(id)
        binding.satelliteName.text = name
    }

    /**
     * Updates UI with satellite details.
     */
    private fun updateDetailUI(detail: SatelliteDetailEntity?) {
        if (detail == null) return
        binding.satelliteCost.text = formatText("Cost: ", "${detail.costPerLaunch}")
        binding.satelliteFirstFlight.text = detail.firstFlight
        binding.satelliteHeightMass.text = formatText("Height/Mass: ", "${detail.height} / ${detail.mass}")
    }

    /**
     * Formats text with bold prefix.
     */
    private fun formatText(title: String, value: String): SpannableStringBuilder {
        return SpannableStringBuilder().apply {
            append(title, StyleSpan(Typeface.BOLD), 0)
            append(value)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}
