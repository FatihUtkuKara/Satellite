package com.example.satellite.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.satellite.R
import com.example.satellite.data.model.Satellite


class SatelliteAdapter(private val onItemClick: (Int) -> Unit) :
    ListAdapter<Satellite, SatelliteAdapter.SatelliteViewHolder>(SatelliteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SatelliteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_satellite, parent, false)
        return SatelliteViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: SatelliteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class SatelliteViewHolder(itemView: View, private val onItemClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val satelliteName: TextView = itemView.findViewById(R.id.satelliteNameTextView)
        private val satelliteStatus: TextView = itemView.findViewById(R.id.satelliteStatusTextView)

        fun bind(satellite: Satellite) {
            satelliteName.text = satellite.name
            satelliteStatus.text = if (satellite.active) "Active" else "Inactive"

            itemView.setOnClickListener {
                onItemClick(satellite.id)
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
