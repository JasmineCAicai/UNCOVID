package com.example.uncovid

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.question_card_layout.view.*

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var titles = arrayOf("Chapter One", "Chapter Two", "Chapter Three", "Chapter Four", "Chapter Five", "Chapter Six", "Chapter Seven", "Chapter Eight")

    private var dates = arrayOf("Jan 1, 2021", "Jan 2, 2021", "Jan 3, 2021", "Jan 4, 2021", "Jan 5, 2021", "Jan 6, 2021", "Jan 7, 2021", "Jan 8, 2021")

    private var images = intArrayOf(R.mipmap.circle, R.mipmap.circle, R.mipmap.circle, R.mipmap.circle, R.mipmap.circle, R.mipmap.circle, R.mipmap.circle, R.mipmap.circle)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]
        holder.itemDate.text = dates[position]
        holder.itemImage.setImageResource(images[position])
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.item_image
        var itemTitle: TextView = itemView.item_title
        var itemDate: TextView = itemView.item_date

    }
}