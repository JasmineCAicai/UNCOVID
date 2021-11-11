package com.example.uncovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_add_answer.*

class AddAnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_answer)

        backAddAnsBtn.setOnClickListener {
            finish()
        }

        addAnsBtn.setOnClickListener {
            ansInputCard.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
    }
}