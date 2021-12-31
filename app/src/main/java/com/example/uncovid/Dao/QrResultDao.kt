package com.example.uncovid.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uncovid.entity.QrResult
import java.util.*

@Dao
interface QrResultDao {

    @Query("SELECT * FROM QrResult ORDER BY time DESC")
    fun getAllScannedResult(): List<QrResult>

    @Query("SELECT result FROM QrResult WHERE result <> 'Text' ORDER BY time DESC")
    fun getAllQRResult(): Array<String>

    @Query("SELECT time FROM QrResult WHERE result <> 'Text' ORDER BY time DESC")
    fun getAllQRTime(): Array<String>

    @Query("SELECT * FROM QrResult WHERE favourite = 1 ORDER BY time DESC")
    fun getAllFavouriteResult(): List<QrResult>

    @Query("DELETE FROM QrResult")
    fun deleteAllScannedResult()

    @Query("DELETE FROM QrResult WHERE favourite = 1")
    fun deleteAllFavouriteResult()

    @Query("DELETE FROM QrResult WHERE id = :id")
    fun deleteQrResult(id: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQrResult(qrResult: QrResult): Long

    @Query("SELECT * FROM QrResult WHERE id = :id")
    fun getQrResult(id: Int): QrResult

    @Query("SELECT COUNT(id) FROM QrResult WHERE result = :result")
    fun getTotal(result: String): Int

    @Query("UPDATE QrResult SET favourite = 1 WHERE id = :id")
    fun addToFavourite(id: Int): Int

    @Query("UPDATE QrResult SET favourite = 0 WHERE id = :id")
    fun removeFromFavourite(id: Int): Int

    @Query("SELECT * FROM QrResult WHERE result = :result ")
    fun checkIfQrResultExist(result: String): Int
}