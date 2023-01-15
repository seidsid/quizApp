package org.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.example.quizapp.R
import org.example.domainmodels.Answer
import org.example.domainmodels.Question
import org.example.domainmodels.Quiz
import org.example.service.QuizService

class QuestionsActivity : AppCompatActivity() {
    var quiz: Quiz? = null
    var correctAnswers = 0
    private val answers: MutableList<Answer> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questions)
        quiz = getIntent().getSerializableExtra(EXTRA_QUIZ) as Quiz?
        if (quiz == null) IllegalAccessException()
        init(quiz!!.getQuestions().iterator())
    }

    @SuppressLint("NewApi")
    private fun constructResultText(): String {
        val totalQuestions = answers.size
        val correctAnswers = answers.stream().filter(Answer::isRight).count();
        val wrongAnswers = totalQuestions - correctAnswers
        return String.format(
            "Total Questions: %d\nCorrect Answers: %d\nWrong Answers: %d\nYour Score is: %d/%d",
            totalQuestions, correctAnswers, wrongAnswers, correctAnswers, totalQuestions
        )
    }

    private fun constructAnalysisText(): String {
        val analysis = StringBuilder()
        for (answer in answers) {
            analysis.append(
                String.format(
                    "%s\nYour Answer: %s\nCorrect Answer: %s\n\n",
                    answer.question.question, answer.choiceText, answer.correctAnswerText
                )
            )
        }
        return analysis.toString()
    }

    private fun init(questionIterator: Iterator<Question>) {
        if (!questionIterator.hasNext()) {
            QuizService.getInstance(getApplicationContext())!!
                .submitNewScore(quiz!!.id, correctAnswers)
            (findViewById(R.id.result_text_view) as TextView).text = constructResultText()
            (findViewById(R.id.result_view_container) as View).setVisibility(View.VISIBLE)
            (findViewById(R.id.analysis_button) as View).setOnClickListener { button ->
                (findViewById(R.id.result_text_view) as TextView).movementMethod =
                    ScrollingMovementMethod()
                (findViewById(R.id.result_text_view) as TextView).text = constructAnalysisText()
            }
            return
        }
        val questionFragment = QuestionFragment()
        val currentQuestion = questionIterator.next()
        questionFragment.setQuestion(currentQuestion)
        questionFragment.setOnAnswerSubmittedListener { answerId: Int? ->
            var answer = 0
            when (answerId) {
                R.id.choice_one_rb -> answer = 0
                R.id.choice_two_rb -> answer = 1
                R.id.choice_three_rb -> answer = 2
                R.id.choice_four_rb -> answer = 3
            }
            answers.add(Answer(currentQuestion, answer))
            if (currentQuestion.check(answer)) correctAnswers++
            init(questionIterator)
        }
        val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()
        transaction.replace(R.id.fragment_container, questionFragment)
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()
    }

    companion object {
        const val EXTRA_QUIZ = "EXTRA_QUIZ"
    }
}