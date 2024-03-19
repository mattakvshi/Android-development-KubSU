package com.example.lessons3

import android.app.Application
import android.content.Context
import com.example.lessons3.repository.DataRepository

class Application34: Application() {

    override fun onCreate() {
        super.onCreate()
        DataRepository.getInstatnce().loadData()
    }

    init{
        instace = this
    }

    companion object{
        private var instace: Application34? = null

        val context
            get() = applicationContext()

        private fun applicationContext() : Context {
            return instace!!.applicationContext
        }
    }
}