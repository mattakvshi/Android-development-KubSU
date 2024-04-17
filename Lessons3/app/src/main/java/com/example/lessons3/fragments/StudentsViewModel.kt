package com.example.lessons3.fragments

import androidx.lifecycle.ViewModel

import androidx.lifecycle.MutableLiveData
import com.example.lessons3.data.Group
import com.example.lessons3.data.Student
import com.example.lessons3.repository.DataRepository

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
}