package com.example.app3

import android.app.Application
import android.content.Context
import com.example.app3.repository.DataRepository

//чтобы связать этот класс с приложением - нужно добавить строку в манифест

class Application34: Application() {

    /*override fun onCreate() {
        super.onCreate()
        DataRepository.getInstance().loadData()
    }*/

    init {
        instance = this
    }

    companion object {
        private var instance: Application34? = null
        val context
            get() = applicationContext()
        private fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}