package com.example.uncovid

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.question_card_layout.view.*

class RecyclerAdapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var titles = arrayOf("What is COVID-19?", "What causes COVID-19?",
        "Where was COVID-19 first discovered?", "How does COVID-19 spread?",
        "What are coronaviruses?", "How do viruses get their name?",
        "In what conditions does COVID-19 survive?", "When COVID-19 will spread more easily?")

    private var dates = arrayOf("Jan 1, 2021", "Jan 2, 2021", "Jan 3, 2021", "Jan 4, 2021",
        "Jan 5, 2021", "Jan 6, 2021", "Jan 7, 2021", "Jan 8, 2021")

    private var images = intArrayOf(R.mipmap.circle, R.mipmap.circle, R.mipmap.circle,
        R.mipmap.circle, R.mipmap.circle, R.mipmap.circle, R.mipmap.circle, R.mipmap.circle)
    
    private var question_id = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7)

    private var answered = booleanArrayOf(true, false, false, false, false, false, false, false)

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

                if (answered[position]) {
                    val intent = Intent(it.context, ViewAnswerActivity::class.java)
                    it.context.startActivity(intent)
                }
                else {
                    val intent = Intent(it.context, AddAnswerActivity::class.java)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}