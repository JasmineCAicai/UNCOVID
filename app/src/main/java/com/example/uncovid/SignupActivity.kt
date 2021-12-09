package com.example.uncovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.uncovid.DB.ReminderDBHelper
import com.example.uncovid.DB.UserDBHelper
import com.example.uncovid.entity.Reminder
import com.example.uncovid.entity.User
import kotlinx.android.synthetic.main.activity_signup.*
import kotlin.random.Random

class SignupActivity : AppCompatActivity() {

    lateinit var userDbHelper: UserDBHelper
    lateinit var reminderDBHelper: ReminderDBHelper

    private var names = arrayOf("Rose", "Jasmine", "Bob", "Eric", "Windy", "Mary")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        userDbHelper = UserDBHelper(this)
        reminderDBHelper = ReminderDBHelper(this)

        tbtnSignup.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        btnSignup.setOnClickListener {
            registerUser()
            initReminder()
            Toast.makeText(this, "Sign up successfully!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {

        var id = idNumber.text.toString()
        var phoneNo = phoneNum_2.text.toString()
        var pwd = password_2.text.toString()

        if (id!!.isEmpty()) {
            idNumber.error = "ID card number required!"
            idNumber.requestFocus()
            return
        }

        if (phoneNo!!.isEmpty()) {
            phoneNum_2.error = "Phone number required!"
            phoneNum_2.requestFocus()
            return
        }

        if (userDbHelper.checkDuplicate(phoneNo)) {
            phoneNum_2.error = "Phone number was registered!"
            phoneNum_2.requestFocus()
            return
        }

        if (pwd!!.isEmpty()) {
            password_2.error = "Password required!"
            password_2.requestFocus()
            return
        }

        var user = User(id, phoneNo, pwd, names[Random.nextInt(0, 5)])

        userDbHelper.insertUserData(user)
        return
    }

    private fun initReminder() {
        var reminder = Reminder(phoneNum_2.text.toString(), 0, 0, 0)
        reminderDBHelper.insertReminderData(reminder)
        return
    }
}