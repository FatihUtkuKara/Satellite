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
import com.example.satellite.presentation.SatelliteAdapter
import com.example.satellite.presentation.SatelliteViewModel
import com.example.satellite.util.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SatelliteViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val progressBar: ProgressBar = findViewById(R.id.progressBar)
        val searchEditText: EditText = findViewById(R.id.searchEditText)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = SatelliteAdapter { satelliteId ->
            val intent = Intent(this, SatelliteDetailActivity::class.java)
            intent.putExtra("satellite_id", satelliteId)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        Log.i("MainActivity", "ViewModel HashCode: ${viewModel.hashCode()}")
        viewModel.isLoading.observe(this) { isLoading ->
            Log.i("ProgressBar", "isLoading: $isLoading")
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.satelliteListResource.observe(this) { resource ->

            when (resource) {

                is Resource.Success -> {
                        Log.i("MainActivity", "List upgrading...")

                        recyclerView.postDelayed({
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

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.updateSearchQuery(s.toString())
            }
        })

        viewModel.loadSatelliteList()



    }
}

