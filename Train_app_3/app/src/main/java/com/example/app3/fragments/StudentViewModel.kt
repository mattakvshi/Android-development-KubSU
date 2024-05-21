package com.example.app3.fragments

import androidx.lifecycle.ViewModel
import com.example.app3.data.Group
import com.example.app3.data.Student
import com.example.app3.repository.DataRepository
import java.util.Date

class StudentViewModel : ViewModel() {

    private var _student: Student? = null
    val student get() = _student

    var group: Group? = null
    //var student: Student? = null

    fun set_Group(group: Group){
        this.group = group
    }

    fun setStudent(student: Student?){
        _student = student
    }

    fun appendStudent(lastName: String, firstName: String, middleName: String, birthDate: Date, phone: String, sex: Int) {
        val student = Student()
        student.lastName = lastName
        student.firstName = firstName
        student.middleName = middleName
        student.birthDate = birthDate
        student.phone = phone
        student.sex = sex
        student.groupID = group!!.id
        DataRepository.getInstance().addStudent(student)
    }

    fun updateStudent(lastName: String, firstName: String, middleName: String, birthDate: Date, phone: String, sex: Int) {
        if (_student != null) {
            _student!!.lastName = lastName
            _student!!.firstName = firstName
            _student!!.middleName = middleName
            _student!!.birthDate = birthDate
            _student!!.phone = phone
            _student!!.sex = sex
            DataRepository.getInstance().updateStudent(_student!!)
        }
    }
}