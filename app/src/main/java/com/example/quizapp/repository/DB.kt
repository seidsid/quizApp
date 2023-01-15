package org.example.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(context: Context?) :
    SQLiteOpenHelper(context, "quiz", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + ID_COLUMN + " Integer PRIMARY KEY," + SCORE_COLUMN + " Integer)")
        val cv = ContentValues()
        cv.put(ID_COLUMN, 0)
        cv.put(SCORE_COLUMN, 0)
        db.insert(TABLE_NAME, null, cv)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    companion object {
        const val TABLE_NAME = "Quiz"
        const val ID_COLUMN = "ID"
        const val SCORE_COLUMN = "score"
    }
}