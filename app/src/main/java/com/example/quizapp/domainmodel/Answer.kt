package org.example.domainmodels

class Answer(val question: Question, private val choice: Int) {

    val isRight: Boolean
        get() = question.check(choice)
    val choiceText: String
        get() = question.choices[choice]
    val correctAnswerText: String
        get() = question.choices[question.answerIdx]
}