package com.example.mystudentapp


const val EXTRA_STUDENT_MODEL = "com.example.UserModel"

class UserModel (
                    firstName : String,
                    middleName : String,
                    lastName : String,
                    birthday : String,
                    phoneNumber : String,
                    course : String,
                    group : String
                ){

    var firstName = ""
    var middleName = ""
    var lastName = ""
    var birthday = ""
    var phoneNumber = ""
    var course = ""
    var group = ""

    init {
        this.firstName =  firstName
        this.middleName = middleName
        this.lastName = lastName
        this.birthday = birthday
        this.phoneNumber = phoneNumber
        this.course = course
        this.group = group
    }

    override fun toString(): String {
        return "Имя: $firstName\n" +
                "Фамилия: $lastName\n" +
                "Отчество: $middleName\n" +
                "День рождения: $birthday\n" +
                "Номер телефона: $phoneNumber\n" +
                "Курс: $course\nГруппа: $group"
    }

    fun parseFromString(inputString: String) {
        val lines = inputString.split("\n")
        for (line in lines) {
            val parts = line.split(": ")
            if (parts.size == 2) {
                when (parts[0]) {
                    "Имя" -> firstName = parts[1]
                    "Фамилия" -> lastName = parts[1]
                    "Отчество" -> middleName = parts[1]
                    "День рождения" -> birthday = parts[1]
                    "Номер телефона" -> phoneNumber = parts[1]
                    "Курс" -> course = parts[1]
                    "Группа" -> group = parts[1]
                }
            }
        }
    }
}