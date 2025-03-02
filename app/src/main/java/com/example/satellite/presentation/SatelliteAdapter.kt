package com.example.satellite.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.satellite.R
import com.example.satellite.data.model.Satellite
import com.example.satellite.databinding.ItemSatelliteBinding


class SatelliteAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<Satellite, SatelliteAdapter.SatelliteViewHolder>(SatelliteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteViewHolder {
        val binding = ItemSatelliteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SatelliteViewHolder(binding,onItemClick)
    }

    override fun onBindViewHolder(holder: SatelliteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SatelliteViewHolder(private val binding: ItemSatelliteBinding, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(satellite: Satellite) {
            binding.satelliteNameTextView.text = satellite.name
            binding.root.setOnClickListener { onItemClick(satellite.id) }



            if (satellite.active) {
                binding.imageViewStatus.setImageResource(R.drawable.baseline_brightness_1_24_green)
                binding.satelliteStatusTextView.text = "Active"
            } else {
                binding.imageViewStatus.setImageResource(R.drawable.baseline_brightness_1_24_red)
                binding.satelliteStatusTextView.text = "Passive"
            }
        }
    }
}

class SatelliteDiffCallback : DiffUtil.ItemCallback<Satellite>() {
    override fun areItemsTheSame(oldItem: Satellite, newItem: Satellite): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Satellite, newItem: Satellite): Boolean {
        return oldItem == newItem
    }
}
