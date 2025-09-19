package data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, "UserDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE users(id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun registerUser(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        return db.insert("users", null, values) > 0
    }

    fun loginUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", arrayOf(username, password))
        val success = cursor.count > 0
        cursor.close()
        return success
    }
}