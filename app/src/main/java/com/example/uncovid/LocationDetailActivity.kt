package com.example.uncovid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.uncovid.DB.CDBHelper
import com.example.uncovid.DB.QrResultDataBase
import com.example.uncovid.entity.DBHelperI
import com.example.uncovid.entity.QrResult
import com.example.uncovid.utils.toFormattedDisplay
import kotlinx.android.synthetic.main.activity_location_detail.*
import kotlinx.android.synthetic.main.activity_reminder.*
import kotlinx.android.synthetic.main.layout_qr_result_show.*

class LocationDetailActivity : AppCompatActivity() {

    private lateinit var dbHelperI: DBHelperI
    private var qrResult: QrResult? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location_detail)

        dbHelperI = CDBHelper(QrResultDataBase.getAppDatabase(this!!)!!)
        qrResult = dbHelperI.getQRResult(intent.getIntExtra("QRid", 1))
        var numPpl = qrResult!!.result?.let { (dbHelperI as CDBHelper).getTotal(it) }

        tvLTime1.text = "Last updated: " + qrResult?.calendar
        tvLocName.text = qrResult!!.result
        tvPpl.text = numPpl.toString()
        tvLTime2.text = "Last updated: " + qrResult?.calendar

        locationBack1Btn.setOnClickListener {
            finish()
        }

    }
}