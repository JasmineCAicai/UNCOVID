package com.example.uncovid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uncovid.utils.getJSONDataFromAsset
import kotlinx.android.synthetic.main.activity_view_answer.*

import android.util.Log
import com.example.uncovid.entity.Answer
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import com.google.gson.Gson

class ViewAnswerActivity : AppCompatActivity() {

    //private var qid = intent.getIntExtra("qid", 0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_answer)

        backViewAnsBtn.setOnClickListener {
            finish()
        }

        var qid = intent.getIntExtra("qid", 0)

        val jsonFileString = getJSONDataFromAsset(applicationContext, "answers.json")
        if (jsonFileString != null) {
            Log.i("data", jsonFileString)
        }
        else {
            Log.i("data", "JSON file is empty or read data failed.")
        }

        val gson = Gson()
        val listAnswerType = object : TypeToken<List<Answer>>() {}.type

        var answers: List<Answer> = gson.fromJson(jsonFileString, listAnswerType)

        answers.forEachIndexed { idx, person -> Log.i("data", "> Item $idx:\n$person") }

        answerTitle.text = answers[qid].title
        answerDate.text = answers[qid].date
        answerContent.text = answers[qid].content
    }
}