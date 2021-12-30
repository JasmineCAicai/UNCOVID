package com.example.uncovid.converters

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import java.util.*

class DateTimeConverters {
    @TypeConverter
    fun toCalendar(l: Long): Calendar? {
        val c = Calendar.getInstance()
        c!!.timeInMillis = l
        return c
    }

    @TypeConverter
    fun fromCalendar(c: Calendar?): Long? {
        return c?.time?.time
    }
}