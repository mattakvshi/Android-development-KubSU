package com.example.mystudentapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider

const val LOG_TAG ="com.example.MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var studentInfoTV: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "MainActivity создан")
        setContentView(R.layout.activity_main)

        studentInfoTV =findViewById(R.id.studentInfoV)

        var userModel : UserModel = UserModel(
            getString(R.string.defaulFirstName),
            getString(R.string.defaulMiddleName),
            getString(R.string.defaulLastName),
            getString(R.string.defaulBirthday),
            getString(R.string.defaulPhoneNumber),
            getString(R.string.defaulCourse),
            getString(R.string.defaulGroup))

        var userModelText = userModel.toString()

        studentInfoTV.text = userModelText

        val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK){
                Log.d(LOG_TAG, "MainActivity результат вернулся")
                val data: Intent? = result.data
                userModelText = data?.getStringExtra(EXTRA_STUDENT_MODEL)?:userModelText
                userModel.parseFromString(userModelText)
                studentInfoTV.text = userModelText
            }
        }

        findViewById<Button>(R.id.changeBtn).setOnClickListener {
            val intent = ChangeActivity.newIntent(this@MainActivity, userModelText)
            Log.d(LOG_TAG, "Нажали кнопку изменить")
            resultLauncher.launch(intent)
        }
    }
}