package com.example.uncovid.entity

interface DBHelperI {
    fun insertQRResult(result: String, IDCard: String): Int

    fun getQRResult(id: Int): QrResult

    fun getTotal(result: String): Int

//    fun getAllQRScannedResult(): List<QrResult>

    fun getAllQRResult(IDCard: String?): Array<String>

    fun getAllQRTime(IDCard: String?): Array<String>

    fun getResult(id: Int): String

}