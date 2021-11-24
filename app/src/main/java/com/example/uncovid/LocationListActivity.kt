package com.example.uncovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import kotlinx.android.synthetic.main.activity_location_list.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_reminder.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class LocationListActivity : AppCompatActivity() {

    private val lastDayInCalendar = Calendar.getInstance(Locale.ENGLISH)
    private val sdf = SimpleDateFormat("MMMM yyyy", Locale.ENGLISH)
    private val cal = Calendar.getInstance(Locale.ENGLISH)

    private val currentDate = Calendar.getInstance(Locale.ENGLISH)
    private val currentDay = currentDate[Calendar.DAY_OF_MONTH]
    private val currentMonth = currentDate[Calendar.MONTH]
    private val currentYear = currentDate[Calendar.YEAR]

    private var selectedDay: Int = currentDay
    private var selectedMonth: Int = currentMonth
    private var selectedYear: Int = currentYear

    private val dates = ArrayList<Date>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_list)

        backLocationBtn.setOnClickListener {
            finish()
        }

        btnScanner.setOnClickListener {
            val intent = Intent(this, ScannerActivity::class.java)
            startActivity(intent)
        }

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(calendar_recycler_view)

        setUpCalendar()

        calendar_prev_button!!.setOnClickListener {
            cal.add(Calendar.MONTH, -1)
            if (cal == currentDate)
                setUpCalendar()
            else
                setUpCalendar(changeMonth = cal)
        }

        calendar_next_button!!.setOnClickListener {
            if (cal.before(lastDayInCalendar)) {
                cal.add(Calendar.MONTH, 1)
                setUpCalendar(changeMonth = cal)
            }
        }
    }

    private fun setUpCalendar(changeMonth: Calendar? = null) {
        // first part
        txt_current_month!!.text = sdf.format(cal.time)
        val monthCalendar = cal.clone() as Calendar

        //val maxDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)

        selectedDay =
            when {
                changeMonth != null -> changeMonth.getActualMinimum(Calendar.DAY_OF_MONTH)
                else -> currentDay
            }
        selectedMonth =
            when {
                changeMonth != null -> changeMonth[Calendar.MONTH]
                else -> currentMonth
            }
        selectedYear =
            when {
                changeMonth != null -> changeMonth[Calendar.YEAR]
                else -> currentYear
            }

        /*get maximal day in the month*/
        val maxDaysInMonth = if (selectedMonth == currentDate[Calendar.MONTH]) {
            currentDate[Calendar.DAY_OF_MONTH]
        } else {
            cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        }

        var currentPosition = 0
        dates.clear()
        monthCalendar.set(Calendar.DAY_OF_MONTH, 1)

        while (dates.size < maxDaysInMonth) {
            if (monthCalendar[Calendar.DAY_OF_MONTH] == selectedDay)
                currentPosition = dates.size
            dates.add(monthCalendar.time)
            monthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        calendar_recycler_view!!.layoutManager = layoutManager
        val calendarAdapter = CalendarAdapter(this, dates, currentDate, changeMonth)
        calendar_recycler_view!!.adapter = calendarAdapter

        when {
            currentPosition > 2 -> calendar_recycler_view!!.scrollToPosition(currentPosition - 3)
            maxDaysInMonth - currentPosition < 2 -> calendar_recycler_view!!.scrollToPosition(currentPosition)
            else -> calendar_recycler_view!!.scrollToPosition(currentPosition)
        }

        calendarAdapter.setOnItemClickListener(object : CalendarAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                var location = arrayOf<String>()
                var time = arrayOf<String>()
                val clickCalendar = Calendar.getInstance()
                clickCalendar.time = dates[position]
                selectedDay = clickCalendar[Calendar.DAY_OF_MONTH]
                if(position == 14){
                    location = arrayOf("Petronas Twin Towers", "Marini’s on 57", "Aquaria KLCC", "Grand Hyatt Kuala Lumpur")
                    time = arrayOf("11:17 AM", "1:34 PM", "2:55 PM", "8:18 PM")
                }
                val locationLayoutManager = LinearLayoutManager(this@LocationListActivity)
                location_recycler_view!!.layoutManager = locationLayoutManager
                val locationAdapter = LocationListAdapter(this@LocationListActivity, location, time)
                location_recycler_view!!.adapter = locationAdapter
            }
        })

    }

}