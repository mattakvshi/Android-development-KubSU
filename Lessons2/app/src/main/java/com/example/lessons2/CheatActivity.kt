package com.example.lessons2

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER_IS_TURE = "com.example.lessons2.answer_is_true"
const val EXTRA_ANSWER_SHOW = "com.example.lessons2.answer_show"

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue = false
    private lateinit var showAnswerButton: Button

    companion object {
        fun newIntent(packagesContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packagesContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TURE, answerIsTrue)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        answerIsTrue=intent?.getBooleanExtra(EXTRA_ANSWER_IS_TURE, false)?:false
        setContentView(R.layout.activity_cheat)

        val answerTextView = findViewById<TextView>(R.id.textViewAnswer)
        answerTextView.visibility = View.INVISIBLE //GONE
        val answerText = when{
            answerIsTrue -> R.string.true_text
            else -> R.string.false_text
        }
        answerTextView.setText(answerText)

        showAnswerButton = findViewById(R.id.btnShow)
        showAnswerButton.setOnClickListener {
            answerTextView.visibility = View.VISIBLE
            showAnswerButton.visibility=View.GONE

            val data = Intent().apply {
                putExtra(EXTRA_ANSWER_SHOW, true)
            }
            setResult(Activity.RESULT_OK, data)

        }

    }
}