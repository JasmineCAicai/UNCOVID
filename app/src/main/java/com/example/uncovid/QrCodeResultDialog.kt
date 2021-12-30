package com.example.uncovid

import android.app.Dialog
import android.content.Context
import com.example.uncovid.entity.QrResult
import com.example.uncovid.utils.toFormattedDisplay
import kotlinx.android.synthetic.main.layout_qr_result_show.*

class QrCodeResultDialog(var context: Context) {

    private lateinit var dialog: Dialog


    private var qrResult: QrResult? = null


    init {
//        init()
        initDialog()
    }


//    private fun init() {
//        dbHelperI = DbHelper(QrResultDataBase.getAppDatabase(context)!!)
//    }

    private fun initDialog() {
        dialog = Dialog(context)
        dialog.setContentView(R.layout.layout_qr_result_show)
        dialog.setCancelable(false)
        onClicks()
    }

    fun show(recentQrResult: QrResult) {
        this.qrResult = recentQrResult
        dialog.scannedText.text = "QR Code: " + qrResult!!.result
        dialog.favouriteIcon.isSelected = qrResult!!.favourite
        dialog.show()
    }

    private fun onClicks() {

        dialog.favouriteIcon.setOnClickListener {
            if (it.isSelected) {

            }
        }

        dialog.copyResult.setOnClickListener {

        }

        dialog.shareResult.setOnClickListener {

        }

        dialog.cancelDialog.setOnClickListener {
            dialog.dismiss()

        }

        dialog.scannedText.setOnClickListener {
//            checkContentAndPerformAction(dialog.scannedText.text.toString())
        }
    }
}