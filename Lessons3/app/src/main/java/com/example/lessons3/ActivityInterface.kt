package com.example.lessons3

import androidx.fragment.app.Fragment
import com.example.lessons3.data.Student

interface ActivityInterface {
    fun updateTitle(newTitle : String)

    fun setFragment(fragmentId: Int, student: Student? = null)
}