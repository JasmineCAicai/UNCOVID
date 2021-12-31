package com.example.uncovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.uncovid.DB.DBHelper
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus

class LoginActivity : AppCompatActivity() {

    lateinit var dbHelper: DBHelper

    //private val model: SharedViewModel by viewModels()

    lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dbHelper = DBHelper(this)

        viewModel = ViewModelProvider(this).get(SharedViewModel::class.java)

        var id = this.intent.getStringExtra("id")
        if (id == "0" && moveTaskToBack(true)) {
            //Toast.makeText(this, "Please login first!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        tbtnLogin.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        btnSignin.setOnClickListener {
            if (dbHelper.userVerify(phoneNum_1.text.toString(), password_1.text.toString())) {

                Toast.makeText(this, "Login successfully!", Toast.LENGTH_SHORT).show()

                viewModel.currentID.value = phoneNum_1.text.toString()

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("id", phoneNum_1.text.toString())
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Phone number or password is wrong!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this);
    }


}

