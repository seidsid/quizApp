package org.example

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.adapter.QuizAdapter
import org.example.domainmodels.Quiz
import org.example.service.QuizService

class MainActivity : AppCompatActivity() {
    var quizAdapter: QuizAdapter? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    protected override fun onStart() {
        super.onStart()
        quizAdapter!!.notifyDataSetChanged()
    }

    private fun init() {
        val rv: RecyclerView = findViewById(R.id.quizz_recycler_view)
        quizAdapter = QuizAdapter(QuizService.getInstance(this)?.game?.getQuizzes())
        quizAdapter!!.setQuizClickListener { view: View?, quiz: Quiz? ->
            onQuizClick(
                view,
                quiz
            )
        }
        rv.setAdapter(quizAdapter)
        rv.setLayoutManager(GridLayoutManager(this, 2))
    }

    private fun onQuizClick(view: View?, quiz: Quiz?) {
        if (quiz!!.isLocked) {
            Toast.makeText(this, "Quiz is locked!", Toast.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, QuestionsActivity::class.java)
            intent.putExtra(QuestionsActivity.EXTRA_QUIZ, quiz)
            startActivity(intent)
        }
    }
}