package com.example.mystudentapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.MediaRouter.UserRouteInfo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity

const val LOG_TAG2 = "com.example.ChangeActivity"

class ChangeActivity : AppCompatActivity() {

    private lateinit var firstNameET: EditText
    private lateinit var middleNameET: EditText
    private lateinit var lastNameET: EditText
    private lateinit var dateET: EditText
    private lateinit var phoneET: EditText
    private lateinit var courseET: EditText
    private lateinit var groupET: EditText

    companion object {
        fun newIntent(packagesContext: Context, userModelText: String): Intent {
            Log.d(LOG_TAG2, "ChangeActivity новый интент")
            return Intent(packagesContext, ChangeActivity::class.java).apply {
                putExtra(EXTRA_STUDENT_MODEL, userModelText)
                Log.d(LOG_TAG2, "ChangeActivity $userModelText")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG2, "ChangeActivity создан")
        setContentView(R.layout.change_activity)

        firstNameET = findViewById(R.id.firstNameET)
        middleNameET = findViewById(R.id.middleNameET)
        lastNameET = findViewById(R.id.lastNameET)
        dateET = findViewById(R.id.dateET)
        phoneET = findViewById(R.id.phoneET)
        courseET = findViewById(R.id.courseET)
        groupET = findViewById(R.id.groupET)

        var userModel : UserModel = UserModel("", "", "", "", "", "", "")
        userModel.parseFromString(intent.getStringExtra(EXTRA_STUDENT_MODEL)!!)

        firstNameET.append(userModel.firstName)
        middleNameET.append(userModel.middleName)
        lastNameET.append(userModel.lastName)
        dateET.append(userModel.birthday)
        phoneET.append(userModel.phoneNumber)
        courseET.append(userModel.course)
        groupET.append(userModel.group)


        val saveBtn = findViewById<Button>(R.id.saveBtn)
        val backBtn = findViewById<Button>(R.id.backBtn)

        backBtn.setOnClickListener {
            val data = Intent().apply {
                putExtra(EXTRA_STUDENT_MODEL, userModel.toString())
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }

        saveBtn.setOnClickListener {
            val firstName = firstNameET.text.toString()
            val middleName = middleNameET.text.toString()
            val lastName = lastNameET.text.toString()
            val date = dateET.text.toString()
            val phone = phoneET.text.toString()
            val course = courseET.text.toString()
            val group = groupET.text.toString()

            if (firstName.isBlank()) {
                showError(firstNameET, "Поле не должно быть пустым")
                return@setOnClickListener
            } else if (!validateName(firstName)) {
                showError(firstNameET,"Фамилия должна содержать только буквы, начинаться с заглавной буквы, и содержать одно слово")
                return@setOnClickListener
            }

            if (middleName.isBlank()) {
                showError(middleNameET, "Поле не должно быть пустым")
                return@setOnClickListener
            }else if (!validateName(middleName)) {
                showError(middleNameET,"Имя должно содержать только буквы, начинаться с заглавной буквы, и содержать одно слово")
                return@setOnClickListener
            }

            if (lastName.isBlank()) {
                showError(lastNameET, "Поле не должно быть пустым")
                return@setOnClickListener
            }else if (!validateName(lastName)) {
                showError(lastNameET,"Отчество должно содержать только буквы, начинаться с заглавной буквы, и содержать одно слово")
                return@setOnClickListener
            }

            if (date.isBlank()) {
                showError(dateET, "Поле не должно быть пустым")
                return@setOnClickListener
            } else if (!validateDate(date)) {
                showError(dateET,"Дата должна быть в формате dd.mm.yyyy")
                return@setOnClickListener
            }

            if (phone.isBlank()) {
                showError(phoneET, "Поле не должно быть пустым")
                return@setOnClickListener
            } else if (!validatePhone(phone)) {
                showError(phoneET,"Некорректный формат номера телефона")
                return@setOnClickListener
            }

            if (course.isBlank()) {
                showError(courseET, "Поле не должно быть пустым")
                return@setOnClickListener
            } else if (!validateCourse(course.toInt())) {
                showError(courseET,"Курс должен быть числом от 1 до 6")
                return@setOnClickListener
            }

            if (groupET.text.toString().isBlank()) {
                showError(groupET, "Поле не должно быть пустым")
                return@setOnClickListener
            }


            val userModelText = "Имя: ${firstNameET.text}\n" +
                    "Фамилия: ${lastNameET.text}\n" +
                    "Отчество: ${middleNameET.text}\n" +
                    "День рождения: ${dateET.text}\n" +
                    "Номер телефона: ${phoneET.text}\n" +
                    "Курс: ${courseET.text}\nГруппа: ${groupET.text}"

            val data = Intent().apply {
                putExtra(EXTRA_STUDENT_MODEL, userModelText)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }

        this.onBackPressedDispatcher.addCallback(this) {
            val data = Intent().apply {
                putExtra(EXTRA_STUDENT_MODEL, userModel.toString())
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }

    fun validateName(input: String): Boolean {
        val regex = Regex("^[A-ZА-Я][A-Za-zА-Яа-я]+\$")
        return regex.matches(input) && input.length > 0
    }

    fun validatePhone(phoneNumber: String): Boolean {
        val regex = Regex("^(\\+7|8)?[-.\\s]?\\(?\\d{3}\\)?[-.\\s]?\\d{3}[-.\\s]?\\d{2}[-.\\s]?\\d{2}\$")
        return regex.matches(phoneNumber)
    }

    fun validateDate(date: String): Boolean {
        val regex = Regex("^\\d{2}\\.\\d{2}\\.\\d{4}\$")

        if (!regex.matches(date)) {
            return false
        }

        val parts = date.split(".")
        val day = parts[0].toInt()
        val month = parts[1].toInt()
        val year = parts[2].toInt()

        if (month !in 1..12) {
            return false
        }

        val maxDaysInMonth = when (month) {
            1, 3, 5, 7, 8, 10, 12 -> 31
            4, 6, 9, 11 -> 30
            2 -> if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0)) 29 else 28
            else -> return false
        }

        return day in 1..maxDaysInMonth
    }

    fun validateCourse(course: Int): Boolean {
        return course in 1..6
    }

    private fun showError(editText: EditText, message: String){
        editText.error = message
        editText.requestFocus()
    }
}