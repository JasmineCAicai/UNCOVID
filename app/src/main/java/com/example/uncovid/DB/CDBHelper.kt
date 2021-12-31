package com.example.uncovid.DB

import com.example.uncovid.entity.DBHelperI
import com.example.uncovid.entity.QrResult
import com.example.uncovid.utils.toFormattedDisplay
import java.util.*

class CDBHelper(var qrResultDataBase: QrResultDataBase) : DBHelperI {

    override fun insertQRResult(result: String, IDCard: String): Int {
        val time = Calendar.getInstance().toFormattedDisplay()
        val resultType = findResultType(result)
        val qrResult = QrResult(result = result, resultType = resultType, IDCard = IDCard, calendar = time)
        return qrResultDataBase.getQrDao().insertQrResult(qrResult).toInt()
    }

    override fun getQRResult(id: Int): QrResult {
        return qrResultDataBase.getQrDao().getQrResult(id)
    }

    override fun getTotal(result: String): Int {
        return qrResultDataBase.getQrDao().getTotal(result)
    }

//    override fun getAllQRScannedResult(): List<QrResult> {
//        return qrResultDataBase.getQrDao().getAllScannedResult()
//    }
    override fun getAllQRResult(IDCard: String?): Array<String> {
        return qrResultDataBase.getQrDao().getAllQRResult(IDCard)
    }
    override fun getAllQRTime(IDCard: String?): Array<String> {
        return qrResultDataBase.getQrDao().getAllQRTime(IDCard)
    }

    override fun getResult(id: Int): String {
        return qrResultDataBase.getQrDao().getResult(id)
    }

    /*
    * This feature will add in future
    * */
    private fun findResultType(result: String): String {
        return "TEXT"
    }

}
