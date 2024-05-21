package com.example.app3.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.app3.data.Faculty
import com.example.app3.data.FacultyList
import com.example.app3.data.University
import com.example.app3.data.UniversityList
import com.example.app3.repository.DataRepository

class FacultyListViewModel : ViewModel() {
    var facultyList: MutableLiveData<List<Faculty>> = MutableLiveData()

    private var _faculty: Faculty? = null

    val faculty get() = _faculty
    val university get() = DataRepository.getInstance().university.value

    private val facultyListObserver = Observer<List<Faculty>> {
            list ->
        facultyList.postValue(
            list.filter {it.universityID == university?.id} as MutableList<Faculty>
        )
    }

    init {
        DataRepository.getInstance().facultyList.observeForever(facultyListObserver)
        DataRepository.getInstance().faculty.observeForever {
            _faculty = it
        }
    }

    fun deleteFaculty() {
        if (faculty != null) {
            DataRepository.getInstance().deleteFaculty(faculty!!)
        }
    }

    fun appendFaculty(facultyName: String) {
        val faculty = Faculty()
        faculty.name = facultyName
        faculty.universityID = DataRepository.getInstance().university.value?.id
        DataRepository.getInstance().newFaculty(faculty)
    }

    fun updateFaculty(facultyName: String) {
        if (_faculty != null) {
            _faculty!!.name = facultyName
            DataRepository.getInstance().updateFaculty(_faculty!!)
        }
    }

    fun setCurrentFaculty(faculty: Faculty) {
        DataRepository.getInstance().setCurrentFaculty(faculty)
    }
}