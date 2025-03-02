package com.example.satellite

import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import com.example.satellite.presentation.SatelliteViewModel
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.text.bold
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
            val textPosition = SpannableStringBuilder()
            textPosition.bold { append("Last Position: ") }
                .append("(${position.first}, ${position.second})")
            binding.satellitePosition.text = textPosition
        }
    }

    private fun updateDetailUI(detail: SatelliteDetailEntity?, satelliteName: String) {
        if (detail == null) return

        binding.satelliteName.text = satelliteName
        val textCost = SpannableStringBuilder()
        textCost.bold { append("Cost: ") }.append("${detail.costPerLaunch}")
        binding.satelliteCost.text = textCost
        binding.satelliteFirstFlight.text = detail.firstFlight
        val textHeightMass = SpannableStringBuilder()
        textHeightMass.bold { append("Height/ Mass: ") }.append("${detail.height} / ${detail.mass}")
        binding.satelliteHeightMass.text = textHeightMass

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
    }
}
