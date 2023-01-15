package org.example.domainmodels

import java.io.Serializable

class Question(
    val question: String, val choices: List<String>, //start from 0
    val answerIdx: Int
) :
    Serializable {

    fun check(answer: Int): Boolean {
        return answer == answerIdx
    }
}