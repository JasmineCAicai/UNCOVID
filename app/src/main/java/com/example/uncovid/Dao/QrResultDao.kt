package com.example.uncovid.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.uncovid.entity.QrResult

@Dao
interface QrResultDao {

//    @Query("SELECT * FROM QrResult ORDER BY time DESC")
//    fun getAllScannedResult(): List<QrResult>

    @Query("SELECT result FROM QrResult WHERE result <> 'Text' AND IDCard= :IDCard ORDER BY time DESC")
    fun getAllQRResult(IDCard: String?): Array<String>

    @Query("SELECT time FROM QrResult WHERE result <> 'Text' AND IDCard= :IDCard ORDER BY time DESC")
    fun getAllQRTime(IDCard: String?): Array<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertQrResult(qrResult: QrResult): Long

    @Query("SELECT * FROM QrResult WHERE id = :id")
    fun getQrResult(id: Int): QrResult

    @Query("SELECT COUNT(id) FROM QrResult WHERE result = :result")
    fun getTotal(result: String): Int

    @Query("SELECT result FROM QrResult WHERE id = :id")
    fun getResult(id: Int): String

    @Query("SELECT * FROM QrResult WHERE result = :result ")
    fun checkIfQrResultExist(result: String): Int
}