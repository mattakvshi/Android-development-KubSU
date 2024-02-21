package com.example.lessons2

import androidx.lifecycle.ViewModel

class MainActivityViewModel : ViewModel() {

    private val questionBank = listOf(
        Questions(R.string.question1,  true),
        Questions(R.string.question2, false),
        Questions(R.string.question3, false),
        Questions(R.string.question4, true),
        Questions(R.string.question5, true)
    )

    var currentIndex = 0
    var isCheater = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestions: Int
        get() = questionBank[currentIndex].textResId

    fun moveToNext() {
        isCheater = false
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev(){
        isCheater = false
        currentIndex = (questionBank.size + currentIndex - 1) % questionBank.size
    }
}