package com.example.uncovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_location_detail.*
import kotlinx.android.synthetic.main.activity_reminder.*

class LocationDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        locationBack1Btn.setOnClickListener {
            finish()
        }

    }
}