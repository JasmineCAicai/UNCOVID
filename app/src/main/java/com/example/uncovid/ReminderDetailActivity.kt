package com.example.uncovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_reminder_detail.*

class ReminderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder_detail)

        showReminderDetail(intent.getIntExtra("option",1))
        remBack2Btn.setOnClickListener {
            finish()
        }
    }

    private fun showReminderDetail(option: Int) {
        when (option) {
            1 -> {
                remDetailTitle.text = "Coronavirus"
                remDetIcon.setImageResource(R.mipmap.coronavirus)
                remDetText.text = "Coronavirus disease (COVID-19) is an infectious disease " +
                        "caused by the SARS-CoV-2 virus.\n Older people and those with " +
                        "underlying medical conditions like cardiovascular disease, diabetes, " +
                        "chronic respiratory disease, or cancer are more likely to develop " +
                        "serious illness. Anyone can get sick with COVID-19 and become seriously " +
                        "ill or die at any age."
            }
            2 -> {
                remDetailTitle.text = "How to Avoid"
                remDetIcon.setImageResource(R.mipmap.avoid)
                remDetText.text = "AAAAAAAAAA AAAAAA AAAAAAAAA AA A AAAAAAAA AAAAAA " +
                        "AAAAA AA AAA SARS-CoV-2 virus.\n Older people and those with " +
                        "underlying medical conditions like cardiovascular disease, diabetes, " +
                        "chronic respiratory disease, or cancer are more likely to develop " +
                        "serious illness. Anyone can get sick with COVID-19 and become seriously " +
                        "ill or die at any age."
            }
            3 -> {
                remDetailTitle.text = "How to Treat"
                remDetIcon.setImageResource(R.mipmap.treat)
                remDetText.text = "TTTTTTTTT TTTTTTT TTTTTTTT TT T TTTTTTTTT TTTTTTT " +
                        "TTTTTT TT TTT SARS-CoV-2 virus.\n Older people and those with " +
                        "underlying medical conditions like cardiovascular disease, diabetes, " +
                        "chronic respiratory disease, or cancer are more likely to develop " +
                        "serious illness. Anyone can get sick with COVID-19 and become seriously " +
                        "ill or die at any age."
            }
        }
    }
}