package org.example.repository

import android.content.ContentValues
import android.content.Context
import org.example.domainmodels.Quiz

class QuizRepository(context: Context?) {
    var db: DB

    init {
        db = DB(context)
    }

    fun unlock(quiz: Quiz) {
        val database = db.writableDatabase
        val cv = ContentValues()
        cv.put(DB.ID_COLUMN, quiz.id)
        cv.put(DB.SCORE_COLUMN, quiz.score)
        database.insert(DB.TABLE_NAME, null, cv)
        database.close()
    }

    fun changeScore(id: Int, score: Int) {
        val database = db.writableDatabase
        val cv = ContentValues()
        cv.put(DB.SCORE_COLUMN, score)
        database.update(DB.TABLE_NAME, cv, DB.ID_COLUMN + "=?", arrayOf(Integer.toString(id)))
        database.close()
    }

    val unlockedQuizzes: List<Tuple>
        get() {
            val list: MutableList<Tuple> = ArrayList()
            val database = db.readableDatabase
            val cursor = database.query(
                DB.TABLE_NAME,
                arrayOf(DB.ID_COLUMN, DB.SCORE_COLUMN),
                null,
                null,
                null,
                null,
                null
            )
            while (cursor.moveToNext()) {
                list.add(Tuple(cursor.getInt(0), cursor.getInt(1)))
            }
            cursor.close()
            database.close()
            return list
        }

    inner class Tuple(val id: Int, val score: Int)
}