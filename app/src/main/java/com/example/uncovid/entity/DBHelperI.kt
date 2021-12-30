package com.example.uncovid.entity

import java.util.*

interface DBHelperI {
    fun insertQRResult(result: String): Int

    fun getQRResult(id: Int): QrResult

    fun getTotal(result: String): Int

    fun addToFavourite(id: Int): Int

    fun removeFromFavourite(id: Int): Int

    fun deleteQrResult(id: Int): Int

    fun getAllQRScannedResult(): List<QrResult>
    fun getAllQRResult(): Array<String>
    fun getAllQRTime(): Array<String>

    fun getAllFavouriteQRScannedResult(): List<QrResult>

    fun deleteAllQRScannedResult()

    fun deleteAllFavouriteQRScannedResult()
}