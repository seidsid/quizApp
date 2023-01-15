package org.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.quizapp.R
import org.example.domainmodels.Question
import java.util.function.Consumer

class QuestionFragment : Fragment() {
    private var question: Question? = null
    private var answerListener: Consumer<Int>? = null
    fun setQuestion(question: Question?) {
        this.question = question
    }

    fun setOnAnswerSubmittedListener(listener: Consumer<Int>?) {
        answerListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (answerListener != null) {
            (getView()?.findViewById(R.id.next_button) as Button).setOnClickListener { nextButton ->
                answerListener!!.accept(
                    (getView()?.findViewById(R.id.choices_radio_group) as RadioGroup).checkedRadioButtonId
                )
            }
        }
        (getView()?.findViewById(R.id.question_text_view) as TextView).text = question!!.question
        (getView()?.findViewById(R.id.choice_one_rb) as Button).text = question!!.choices[0]
        (getView()?.findViewById(R.id.choice_two_rb) as Button).text = question!!.choices[1]
        (getView()?.findViewById(R.id.choice_three_rb) as Button).text = question!!.choices[2]
        (getView()?.findViewById(R.id.choice_four_rb) as Button).text = question!!.choices[3]
    }
}