package com.example.lessons3.fragments

import androidx.lifecycle.ViewModel

import androidx.lifecycle.MutableLiveData
import com.example.lessons3.data.Group
import com.example.lessons3.data.Student
import com.example.lessons3.repository.DataRepository
import java.util.Date

class StudentsViewModel : ViewModel() {
    var studentList: MutableLiveData<List<Student>> = MutableLiveData()

    private var _student: Student? = null
    val student get() = _student

    var group: Group? = null

    fun set_Group(group: Group) {
        this.group = group
        DataRepository.getInstance().listOfStudent.observeForever {
            studentList.postValue(
                it.filter {it.groupID == group.id} as MutableList<Student>
            )
        }
        DataRepository.getInstance().student.observeForever {
            _student = it
        }
    }

    fun deleteStudent() {
        if (student != null)
            DataRepository.getInstance().deleteStudent(student!!)
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

    fun setCurrentStudent(student: Student) {
        DataRepository.getInstance().setCurrentStudent(student)
    }

}