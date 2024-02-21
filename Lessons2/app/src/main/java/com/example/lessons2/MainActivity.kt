package com.example.lessons2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import  android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

const val LOG_TAG ="com.example.MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var btnTrue : Button;
    private lateinit var btnFalse : Button;
    private lateinit var ibNext : ImageButton;
    private lateinit var ibPrev : ImageButton;
    private lateinit var questionTextView : TextView;

    private val quizViewModel: MainActivityViewModel by lazy {
        val provider = ViewModelProvider(this)
        provider.get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "MainActivity создан")
        setContentView(R.layout.activity_main)

        btnTrue = findViewById(R.id.btnTrue)
        btnFalse = findViewById(R.id.btnFalse)
        ibNext = findViewById(R.id.ibNext)
        ibPrev = findViewById(R.id.ibPrev)
        questionTextView = findViewById(R.id.questionTextView)

        updateQuestion()

        btnTrue.setOnClickListener {
            checkAnswer(true)
        }

        btnFalse.setOnClickListener {
            checkAnswer(false)
        }

        ibNext.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        ibPrev.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data: Intent? = result.data
                quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOW, false) ?: false
            }
        }

        findViewById<Button>(R.id.btnCheat).setOnClickListener {
            val intent = CheatActivity.newIntent(this@MainActivity, quizViewModel.currentQuestionAnswer)
            resultLauncher.launch(intent)
        }

    }

    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestions
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer : Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val isCheat = quizViewModel.isCheater
        val messageResId = when {
            isCheat -> R.string.cheater_text
            userAnswer == correctAnswer -> R.string.true_text
            else -> R.string.false_text
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
            .show()
    }
}