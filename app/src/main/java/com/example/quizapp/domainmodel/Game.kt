package org.example.domainmodels

class Game(quizzes: Map<Int, Quiz>, totalPoints: Int) {
    private val quizzes: Map<Int, Quiz>
    var totalPoints = 0

    init {
        this.quizzes = quizzes
        this.totalPoints = totalPoints
    }

    fun getQuizzes(): Map<Int, Quiz> {
        return quizzes
    }
}