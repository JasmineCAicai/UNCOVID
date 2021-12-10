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
                remDetailTitle.text = "How to Prevent"
                remDetIcon.setImageResource(R.mipmap.avoid)
                remDetText.text = "1. Maintain a safe distance from others (at least 1 metre), even if they don’t appear to be sick.\n" +
                        "2. Wear a mask in public, especially indoors or when physical distancing is not possible.\n" +
                        "3. Choose open, well-ventilated spaces over closed ones. Open a window if indoors.\n" +
                        "4. Clean your hands often. Use soap and water, or an alcohol-based hand rub.\n" +
                        "5. Get vaccinated when it’s your turn. Follow local guidance about vaccination."
            }
            3 -> {
                remDetailTitle.text = "How to Treat"
                remDetIcon.setImageResource(R.mipmap.treat)
                remDetText.text = "Scientists around the world are working to find and develop treatments for COVID-19.\n" +
                        "Optimal supportive care includes oxygen for severely ill patients and those who are at risk for severe disease and more advanced respiratory support" +
                        " such as ventilation for patients who are critically ill.\n" +
                        "Dexamethasone is a corticosteroid that can help reduce the length of time on a ventilator and save lives of patients with severe and critical illness."
            }
        }
    }
}