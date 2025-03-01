package com.example.satellite

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.satellite.presentation.SatelliteAdapter
import com.example.satellite.presentation.SatelliteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SatelliteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



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