package com.example.uncovid.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.uncovid.entity.Reminder
import com.example.uncovid.entity.User

class DBHelper(context: Context) : SQLiteOpenHelper(context, dbname, factory, version){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table users(phoneNo varchar(30) primary key, " +
                "id varchar(30), password varchar(20), name varchar(30));")
        db?.execSQL("create table reminders(phoneNo varchar(30) primary key, " +
                "rem1 INT NOT NULL, rem2 INT NOT NULL, rem3 INT NOT NULL);")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("drop Table if exists users")
        db?.execSQL("drop Table if exists reminders")
    }


    // User
    fun insertUserData(user: User) {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()
        values.put("phoneNo", user.phoneNo)
        values.put("id", user.id)
        values.put("password", user.password)
        values.put("name", user.name)

        db.insert("users", null, values)
        db.close()
    }

    fun userVerify(phoneNo: String, password: String): Boolean {
        val db = writableDatabase
        val query = "select * from users where phoneNo = \"$phoneNo\" and password = \"$password\";"
        val cursor = db.rawQuery(query, null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    fun checkDuplicate(phoneNo: String): Boolean {
        val db = writableDatabase
        val query = "select * from users where phoneNo = \"$phoneNo\";"
        val cursor = db.rawQuery(query, null)
        if (cursor.count <= 0) {
            cursor.close()
            return false
        }
        cursor.close()
        return true
    }

    fun getUserName(id: String): String {
        var name = ""
        val db = writableDatabase
        val query = "select * from users where id = \"$id\";"
        val cursor = db.rawQuery(query, null)
        if (cursor.count <= 0) {
            cursor.close()
            return "Wrong User"
        }
        else {
            while (cursor.moveToNext()) {
                name = cursor.getString(3)
            }
        }
        cursor.close()
        return name
    }


    // Reminder
    fun insertReminderData(reminder: Reminder) {
        val db: SQLiteDatabase = writableDatabase
        val values: ContentValues = ContentValues()

        values.put("phoneNo", reminder.phoneNo)
        values.put("rem1", reminder.rem1)
        values.put("rem2", reminder.rem2)
        values.put("rem3", reminder.rem3)

        db.insert("reminders", null, values)
        db.close()
    }

    fun getReminderData(phoneNo: String): Reminder {
        val r = Reminder(phoneNo, 0, 0, 0)
        val db = writableDatabase
        val query = "select * from users where phoneNo = \"$phoneNo\";"
        val cursor = db.rawQuery(query, null)
        if (cursor.count <= 0) {
            cursor.close()
            return Reminder(phoneNo, 0, 0, 0)
        }
        else {
            while (cursor.moveToNext()) {
                r.rem1 = cursor.getInt(1)
                r.rem2 = cursor.getInt(2)
                r.rem3 = cursor.getInt(3)
            }
        }
        cursor.close()
        return r
    }

    fun updateReminderData(reminder: Reminder) {
        val db = writableDatabase
        val query = "update reminders " +
                "set rem1 = ${reminder.rem1}, rem2 = ${reminder.rem2}, rem3 = ${reminder.rem3} " +
                "where phoneNo = \"${reminder.phoneNo}\";"
        db.execSQL(query)
        db.close()
    }


    companion object {
        internal val dbname = "uncovidDB"
        internal val factory = null
        internal val version = 2
    }
}