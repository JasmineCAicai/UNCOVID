package com.example.uncovid.utils

import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun View.inVisible() {
    this.visibility = View.INVISIBLE
}

fun Calendar.toFormattedDisplay(): String {
    val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm a", Locale.UK)
    return simpleDateFormat.format(this.time)
}