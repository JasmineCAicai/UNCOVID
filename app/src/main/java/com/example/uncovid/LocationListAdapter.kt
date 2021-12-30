package com.example.uncovid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uncovid.utils.toFormattedDisplay
import java.util.*

class LocationListAdapter(private val context: Context,
                          private val location: Array<String>?,
                          private val time: Array<String>?): RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemLocation: TextView = itemView.findViewById(R.id.tvLocation)
        var itemTime :TextView = itemView.findViewById(R.id.tvTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.custom_location_card,parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(location != null)
            holder.itemLocation.text = location!![position]
        if(time != null)
            holder.itemTime.text = time!![position]
    }

    override fun getItemCount(): Int {
        if (location != null) {
            return location.size
        }
        return 0
    }
}