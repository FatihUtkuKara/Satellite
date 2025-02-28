package com.example.satellite

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.satellite.data.repository.SatelliteRepository
import com.example.satellite.presentation.SatelliteAdapter
import com.example.satellite.presentation.SatelliteViewModel
import com.example.satellite.presentation.SatelliteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: SatelliteViewModel
    private lateinit var repository: SatelliteRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = SatelliteRepository.getInstance(this) // Singleton Repository
        val factory = SatelliteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[SatelliteViewModel::class.java]

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = SatelliteAdapter { satelliteId ->
            val intent = Intent(this, SatelliteDetailActivity::class.java)
            intent.putExtra("satellite_id", satelliteId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        viewModel.loadSatelliteList()

        viewModel.satelliteList.observe(this) { satellites ->
            adapter.submitList(satellites)
        }
    }
}