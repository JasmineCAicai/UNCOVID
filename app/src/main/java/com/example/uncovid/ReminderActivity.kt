package com.example.uncovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        remBack1Btn.setOnClickListener {
            finish()
        }

        knowCorBtn.setOnClickListener {
            viewReminderDetail(1)
        }
        knowAvoidBtn.setOnClickListener {
            viewReminderDetail(2)
        }
        knowTreatBtn.setOnClickListener {
            viewReminderDetail(3)
        }
    }

    private fun viewReminderDetail(option: Int) {
        val intent = Intent(this, ReminderDetailActivity::class.java)
        intent.putExtra("option", option)
        startActivity(intent)
    }
}