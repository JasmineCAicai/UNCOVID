package com.example.uncovid.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.uncovid.entity.User

class UserDBHelper(context: Context) : SQLiteOpenHelper(context, dbname, factory, version){
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("create table users(phoneNo varchar(30) primary key, " +
                "id varchar(30), password varchar(20), name varchar(30));")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

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



    companion object {
        internal val dbname = "uncovidDB"
        internal val factory = null
        internal val version = 2
    }
}