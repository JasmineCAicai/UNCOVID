package com.example.uncovid.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.uncovid.Dao.QrResultDao
import com.example.uncovid.entity.QrResult

@Database(entities = [QrResult::class], version = 4,exportSchema = false)
abstract class QrResultDataBase : RoomDatabase() {
    abstract fun getQrDao(): QrResultDao

    companion object {
        private const val DB_NAME = "QrResultDatabase"
        private var qrResultDataBase: QrResultDataBase? = null
        fun getAppDatabase(context: Context): QrResultDataBase? {
            if (qrResultDataBase == null) {
                qrResultDataBase =
                    Room.databaseBuilder(context.applicationContext, QrResultDataBase::class.java, DB_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
            }
            return qrResultDataBase
        }

        fun destroyInstance() {
            qrResultDataBase = null
        }
    }
}