package com.example.uncovid

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_calendar_day.view.*
import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(private val context: Context,
                      private val data: ArrayList<Date>,
                      private val currentDate: Calendar,
                      private val changeMonth: Calendar?): RecyclerView.Adapter<CalendarAdapter.ViewHolder>() {
    private var mListener: OnItemClickListener? = null
    private var index = -1
    private var selectCurrentDate = true
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val selectedDay =
        when {
            changeMonth != null -> changeMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
            else -> currentDay
        }
    private val selectedMonth =
        when {
            changeMonth != null -> changeMonth[Calendar.MONTH]
            else -> currentMonth
        }
    private val selectedYear =
        when {
            changeMonth != null -> changeMonth[Calendar.YEAR]
            else -> currentYear
        }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        return ViewHolder(inflater.inflate(R.layout.custom_calendar_day, parent, false), mListener!!)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss", Locale.ENGLISH)
        val cal = Calendar.getInstance()
        cal.time = data[position]
        val displayMonth = cal[Calendar.MONTH]
        val displayYear= cal[Calendar.YEAR]
        val displayDay = cal[Calendar.DAY_OF_MONTH]

        try {
            sdf.applyPattern("EEE")
            holder.txtDayInWeek!!.text = DateFormatSymbols().shortWeekdays[cal.get(Calendar.DAY_OF_WEEK)]

        } catch (ex: ParseException) {
            Log.v("Exception", ex.localizedMessage!!)
        }
        holder.txtDay!!.text = cal[Calendar.DAY_OF_MONTH].toString()
        // we will add onClickListener here

        if (displayYear <= currentYear)
            if (displayMonth <= currentMonth || displayYear < currentYear)
                if (displayDay <= currentDay || displayMonth < currentMonth || displayYear < currentYear) {
                    holder.linearLayout!!.setOnClickListener {
                        index = position
                        selectCurrentDate = false
                        holder.listener.onItemClick(position)
                        notifyDataSetChanged()
                    }

                    if (index == position)
                        makeItemSelected(holder)
                    else {
                        if (displayDay == selectedDay
                            && displayMonth == selectedMonth
                            && displayYear == selectedYear
                            && selectCurrentDate)
                            makeItemSelected(holder)
                        else
                            makeItemDefault(holder)
                    }
                }
    }

    inner class ViewHolder(itemView: View, val listener: OnItemClickListener): RecyclerView.ViewHolder(itemView) {
        var txtDay = itemView.txt_date
        var txtDayInWeek = itemView.txt_day
        var linearLayout = itemView.calendar_linear_layout
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    private fun makeItemSelected(holder: ViewHolder) {
        holder.txtDay!!.setTextColor(Color.parseColor("#FFFFFF"))
        holder.txtDayInWeek!!.setTextColor(Color.parseColor("#FFFFFF"))
        holder.linearLayout!!.setBackgroundResource(R.drawable.calendar_selected_bg)
        holder.linearLayout!!.isEnabled = false
    }

    private fun makeItemDefault(holder: ViewHolder) {
        holder.txtDay!!.setTextColor(Color.BLACK)
        holder.txtDayInWeek!!.setTextColor(Color.BLACK)
        holder.linearLayout!!.setBackgroundResource(R.drawable.calendar_default_bg)
        holder.linearLayout!!.isEnabled = true
    }

}