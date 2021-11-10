package com.example.uncovid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.question_card_layout.view.*

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var titles = arrayOf("What is COVID-19?", "What is COVID-19?",
        "What is COVID-19?", "What is COVID-19?",
        "What is COVID-19?", "What is COVID-19?",
        "What is COVID-19?", "What is COVID-19?")

    private var dates = arrayOf("Jan 1, 2021", "Jan 2, 2021", "Jan 3, 2021", "Jan 4, 2021",
        "Jan 5, 2021", "Jan 6, 2021", "Jan 7, 2021", "Jan 8, 2021")

    private var images = intArrayOf(R.mipmap.circle, R.mipmap.circle, R.mipmap.circle,
        R.mipmap.circle, R.mipmap.circle, R.mipmap.circle, R.mipmap.circle, R.mipmap.circle)

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

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition

                Toast.makeText(itemView.context, "you clicked on ${titles[position]}", Toast.LENGTH_LONG).show()

                val intent = Intent(it.context, ViewAnswerActivity::class.java)
                it.context.startActivity(intent)
            }
        }
    }
}