package com.example.app3

import com.example.app3.data.Student

interface ActivityInterface {
    fun updateTitle(newTitle: String)
    fun setFragment(fragmentId: Int, student: Student? = null)
    fun onBack()
}