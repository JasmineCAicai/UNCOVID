package com.example.uncovid

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.uncovid.entity.Answer
import kotlinx.android.synthetic.main.question_card_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapter(private var titleList: ArrayList<Answer>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>(), Filterable {

    /*
    private var titleList = arrayOf(Answer(0, "What is COVID-19?", "Jan 1, 2021", ""),
        Answer(1, "What causes COVID-19?", "Jan 2, 2021", ""),
        Answer(2, "Where was COVID-19 first discovered?", "Jan 3, 2021", ""),
        Answer(3, "How does COVID-19 spread?", "Jan 4, 2021", ""),
        Answer(4, "What are coronaviruses?", "Jan 5, 2021", ""),
        Answer(5, "How do viruses get their name?", "Jan 6, 2021", ""),
        Answer(6, "In what conditions does COVID-19 survive?", "Jan 7, 2021", ""),
        Answer(7, "When COVID-19 will spread more easily?", "Jan 8, 2021", ""),)

     */

    //private var titleList = ArrayList<Answer>()


    var newList : ArrayList<Answer> = titleList

    init {
        newList = titleList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = newList[position].title
        holder.itemDate.text = newList[position].date
        holder.itemImage.setImageResource(R.mipmap.circle)
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    newList = titleList
                } else {
                    val resultList = ArrayList<Answer>()
                    for (row in titleList) {
                        if (row.title.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    newList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = newList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                newList = results?.values as ArrayList<Answer>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var itemImage: ImageView = itemView.item_image
        var itemTitle: TextView = itemView.item_title
        var itemDate: TextView = itemView.item_date

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition

                //Toast.makeText(itemView.context, "you clicked on ${titles[position]}", Toast.LENGTH_LONG).show()
                Toast.makeText(itemView.context, "you clicked on ${newList[position].title}", Toast.LENGTH_LONG).show()

                val intent = Intent(it.context, ViewAnswerActivity::class.java)
                intent.putExtra("qid", newList[position].qid)
                it.context.startActivity(intent)

                /*
                if (answered[position]) {
                    val intent = Intent(it.context, ViewAnswerActivity::class.java)
                    it.context.startActivity(intent)
                }
                else {
                    val intent = Intent(it.context, AddAnswerActivity::class.java)
                    it.context.startActivity(intent)
                }
                 */
            }
        }
    }
}