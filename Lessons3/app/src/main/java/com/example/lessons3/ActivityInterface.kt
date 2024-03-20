package com.example.lessons3

import androidx.fragment.app.Fragment

interface ActivityInterface {
    fun updateTitle(newTitle : String)

    fun setFragment(fragmentId: Int)
}