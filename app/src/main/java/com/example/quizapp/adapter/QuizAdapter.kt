package org.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.quizapp.R
import org.example.domainmodels.Quiz
import org.example.service.QuizService

class QuizAdapter(private val quizMap: Map<Int, Quiz>?) :
    RecyclerView.Adapter<QuizAdapter.ViewHolder?>() {
    fun interface QuizClickListener {
        fun onQuizClick(view: View?, quiz: Quiz?)
    }

    private var quizClickListener: QuizClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.quiz_layout, viewGroup, false) as CardView
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.bind(quizMap?.get(i))
    }

    override fun getItemCount(): Int {
        return quizMap?.size ?: 0
    }

    fun setQuizClickListener(quizClickListener: QuizClickListener) {
        this.quizClickListener = quizClickListener
    }

    inner class ViewHolder(itemView: CardView) : RecyclerView.ViewHolder(itemView) {
        private val lockImageView: ImageView
        private val quizTitleTextView: TextView
        private val scoreTextView: TextView
        private val pointsRequiredTextView: TextView
        private val cardView: CardView

        init {
            lockImageView = itemView.findViewById(R.id.lock_imageview)
            quizTitleTextView = itemView.findViewById(R.id.quiz_title_textview)
            scoreTextView = itemView.findViewById(R.id.score_textview)
            pointsRequiredTextView = itemView.findViewById(R.id.points_required_textview)
            cardView = itemView
        }

        fun bind(quiz: Quiz?) {
            if (quiz!!.isLocked) {
                scoreTextView.visibility = View.INVISIBLE
                lockImageView.setImageResource(R.drawable.lock_locked)
            } else {
                scoreTextView.visibility = View.VISIBLE
                scoreTextView.text = "Score:" + quiz.score
                lockImageView.setImageResource(R.drawable.lock_unlocked)
            }
            pointsRequiredTextView.text = "Points Required:" + quiz.points_required
            quizTitleTextView.text = "Quiz " + (quiz.id + 1)
            if (quizClickListener != null) {
                cardView.setOnClickListener { v -> quizClickListener!!.onQuizClick(v, quiz) }
            }
        }
    }
}