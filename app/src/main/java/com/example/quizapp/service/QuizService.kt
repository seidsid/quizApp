package org.example.service

import android.content.Context
import org.example.domainmodels.Game
import org.example.domainmodels.Question
import org.example.domainmodels.Quiz
import org.example.repository.QuizRepository
import java.util.*

class QuizService private constructor(context: Context) {
    var quizRepository: QuizRepository
    var game: Game

    init {
        quizRepository = QuizRepository(context)
        game = Game(createQuiz(), 0)
        sync()
    }

    fun submitNewScore(id: Int, newScore: Int): Boolean {
        val quiz = game.getQuizzes()[id]
        val diff = newScore - quiz!!.score
        if (quiz.setScore(newScore)) {
            quizRepository.changeScore(id, newScore)
            game.totalPoints = game.totalPoints + diff
            unlockStartingFrom(id + 1)
            return true
        }
        return false
    }

    private fun unlockStartingFrom(id: Int) {
        var id = id
        var temp: Quiz?
        while (true) {
            temp = game.getQuizzes()[id++]
            if (temp == null || temp.points_required > game.totalPoints) break
            if (temp.isLocked) quizRepository.unlock(temp)
            temp.isLocked = false
        }
    }

    private fun createQuiz(): Map<Int, Quiz> {
        val quizMap: MutableMap<Int, Quiz> = HashMap()
        val numberOfQuizzes = 30
        for (i in 0 until numberOfQuizzes) {
            quizMap[i] = Quiz(createQuestions(i), i * 5, if (i == 0) false else true, i, 0)
        }
        return quizMap
    }

    private fun sync() {
        val unlockedQuizzes = quizRepository.unlockedQuizzes
        var tempQuiz: Quiz?
        var totalScore = 0
        for (i in unlockedQuizzes) {
            tempQuiz = game.getQuizzes()[i.id]
            tempQuiz!!.isLocked = false
            tempQuiz.setScore(i.score)
            totalScore += i.score
        }
        game.totalPoints = totalScore
    }

    private fun createQuestions(quizId: Int): List<Question> {
        val questions: MutableList<Question> = ArrayList()
        if (true) {
            questions.add(Question("navigate", Arrays.asList("move", "stop", "up", "down"), 0))
            questions.add(Question("navigate", Arrays.asList("Down", "Ground", "Zoom", "Out"), 0))
            questions.add(Question("navigate", Arrays.asList("move", "stop", "up", "down"), 0))
            questions.add(Question("navigate", Arrays.asList("Down", "Ground", "Zoom", "Out"), 0))
            questions.add(Question("navigate", Arrays.asList("move", "stop", "up", "down"), 0))
            questions.add(Question("navigate", Arrays.asList("Down", "Ground", "Zoom", "Out"), 0))
            questions.add(Question("navigate", Arrays.asList("move", "stop", "up", "down"), 0))
            questions.add(Question("navigate", Arrays.asList("Down", "Ground", "Zoom", "Out"), 0))
            questions.add(Question("navigate", Arrays.asList("move", "stop", "up", "down"), 0))
            questions.add(Question("navigate", Arrays.asList("Down", "Ground", "Zoom", "Out"), 0))
            questions.add(Question("navigate", Arrays.asList("move", "stop", "up", "down"), 0))
            questions.add(Question("navigate", Arrays.asList("Down", "Ground", "Zoom", "Out"), 0))
            questions.add(Question("navigate", Arrays.asList("move", "stop", "up", "down"), 0))
            questions.add(Question("navigate", Arrays.asList("Down", "Ground", "Zoom", "Out"), 0))
            questions.add(Question("navigate", Arrays.asList("move", "stop", "up", "down"), 0))
            questions.add(Question("navigate", Arrays.asList("Down", "Ground", "Zoom", "Out"), 0))
        }
        return questions
    }

    companion object {
        private var quizService: QuizService? = null
        fun getInstance(context: Context): QuizService? {
            synchronized(QuizService::class.java) {
                if (quizService == null) quizService =
                    QuizService(context)
            }
            return quizService
        }
    }
}