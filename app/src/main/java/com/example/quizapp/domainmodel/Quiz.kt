package org.example.domainmodels

import java.io.Serializable

class Quiz(
    questions: List<Question>,
    points_required: Int,
    isLocked: Boolean,
    id: Int,
    score: Int
) :
    Serializable {
    private val questions: List<Question>
    val points_required: Int
    var isLocked: Boolean
    val id: Int
    var score: Int
        private set

    init {
        this.questions = questions
        this.points_required = points_required
        this.isLocked = isLocked
        this.id = id
        this.score = score
    }

    //return true if the score gets changed
    fun setScore(score: Int): Boolean {
        if (score > this.score) {
            this.score = score
            return true
        }
        return false
    }

    fun getQuestions(): List<Question> {
        return questions
    }
}