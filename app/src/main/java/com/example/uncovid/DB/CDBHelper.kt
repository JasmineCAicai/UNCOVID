package com.example.uncovid.DB

import com.example.uncovid.entity.DBHelperI
import com.example.uncovid.entity.QrResult
import com.example.uncovid.utils.toFormattedDisplay
import java.util.*

class CDBHelper(var qrResultDataBase: QrResultDataBase) : DBHelperI {

    override fun insertQRResult(result: String): Int {
        val time = Calendar.getInstance().toFormattedDisplay()
        val resultType = findResultType(result)
        val qrResult = QrResult(result = result, resultType = resultType, calendar = time)
        return qrResultDataBase.getQrDao().insertQrResult(qrResult).toInt()
    }

    override fun getQRResult(id: Int): QrResult {
        return qrResultDataBase.getQrDao().getQrResult(id)
    }

    override fun getTotal(result: String): Int {
        return qrResultDataBase.getQrDao().getTotal(result)
    }

    override fun addToFavourite(id: Int): Int {
        return qrResultDataBase.getQrDao().addToFavourite(id)
    }

    override fun removeFromFavourite(id: Int): Int {
        return qrResultDataBase.getQrDao().removeFromFavourite(id)
    }

    override fun deleteQrResult(id: Int): Int {
        return qrResultDataBase.getQrDao().deleteQrResult(id)
    }

    override fun getAllQRScannedResult(): List<QrResult> {
        return qrResultDataBase.getQrDao().getAllScannedResult()
    }
    override fun getAllQRResult(): Array<String> {
        return qrResultDataBase.getQrDao().getAllQRResult()
    }
    override fun getAllQRTime(): Array<String> {
        return qrResultDataBase.getQrDao().getAllQRTime()
    }

    override fun getAllFavouriteQRScannedResult(): List<QrResult> {
        return qrResultDataBase.getQrDao().getAllFavouriteResult()
    }

    override fun deleteAllQRScannedResult() {
        qrResultDataBase.getQrDao().deleteAllScannedResult()
    }

    override fun deleteAllFavouriteQRScannedResult() {
        qrResultDataBase.getQrDao().deleteAllFavouriteResult()
    }

    /*
    * This feature will add in future
    * */
    private fun findResultType(result: String): String {
        return "TEXT"
    }



}
