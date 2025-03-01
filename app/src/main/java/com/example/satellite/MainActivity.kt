package com.example.satellite

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.satellite.databinding.ActivityMainBinding
import com.example.satellite.presentation.SatelliteAdapter
import com.example.satellite.presentation.SatelliteViewModel
import com.example.satellite.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: SatelliteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SatelliteAdapter { satelliteId ->
            val intent = Intent(this, SatelliteDetailActivity::class.java)
            intent.putExtra("satellite_id", satelliteId)
            startActivity(intent)
        }
        binding.recyclerView.adapter = adapter

        Log.i("MainActivity", "ViewModel HashCode: ${viewModel.hashCode()}")
        viewModel.isLoading.observe(this) { isLoading ->
            Log.i("ProgressBar", "isLoading: $isLoading")
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.satelliteListResource.observe(this) { resource ->

            when (resource) {

                is Resource.Success -> {
                        Log.i("MainActivity", "List upgrading...")

                    binding.recyclerView.postDelayed({
                            adapter.submitList(resource.data)
                            Log.i("MainActivity", "List sent to adapter.")
                        }, 200)
                }
                is Resource.Error -> {
                    Toast.makeText(this, "Error: ${resource.message}", Toast.LENGTH_SHORT).show()

                }
                is Resource.Loading -> {
                    Log.i("MainActivity", "Resource loading")
                }
                }

            }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchQuery(s.toString())
            }
        })

        viewModel.loadSatelliteList()



    }
}

