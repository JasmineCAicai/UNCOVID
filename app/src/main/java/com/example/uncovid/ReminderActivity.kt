package com.example.uncovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.uncovid.DB.DBHelper
import com.example.uncovid.entity.Reminder
import kotlinx.android.synthetic.main.activity_reminder.*

class ReminderActivity : AppCompatActivity() {

    private val model: SharedViewModel by viewModels()

    lateinit var dbHelper: DBHelper

    //private var reminder = Reminder("", 0, 0, 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        dbHelper = DBHelper(this)

        remBack1Btn.setOnClickListener {
            finish()
        }

        var _id = "111111"
        val idObserver = Observer<String>{ id ->
            _id = id
        }
        model.currentID.observe(this, idObserver)


        var id = this.intent.getStringExtra("id")
        var reminder : Reminder = dbHelper.getReminderData(id!!)

        remVacBtn.isChecked = reminder.rem1 == 1
        remNucBtn.isChecked = reminder.rem2 == 1
        remTemBtn.isChecked = reminder.rem3 == 1

        remVacBtn.setOnCheckedChangeListener{ _, _ ->
            if (remVacBtn.isChecked) {
                reminder.rem1 = 1
                dbHelper.updateReminderData(reminder)
                println("Check Vac task successful!")
            }
        }

        remNucBtn.setOnCheckedChangeListener{ _, _ ->
            if (remNucBtn.isChecked) {
                reminder.rem2 = 1
                dbHelper.updateReminderData(reminder)
                println("Check Nuc task successful!")
            }
        }

        remTemBtn.setOnCheckedChangeListener{ _, _ ->
            if (remTemBtn.isChecked) {
                reminder.rem3 = 1
                dbHelper.updateReminderData(reminder)
                println("Check Tem task successful!")
            }
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