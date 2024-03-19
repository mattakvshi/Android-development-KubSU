package com.example.lessons3.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.lessons3.data.Faculty
import com.example.lessons3.data.FacultyList
import com.example.lessons3.data.University
import com.example.lessons3.data.UniversityList
import com.example.lessons3.repository.DataRepository

class FacultyListViewModel : ViewModel() {
    var facultyList: MutableLiveData<FacultyList?> = MutableLiveData()

    private var _faculty: Faculty? = null

    val faculty
        get()=_faculty

    private val facultyListObserver = Observer<FacultyList?>{
            list ->
        facultyList.postValue(list)
    }

    init{
        DataRepository.getInstatnce().facultyList.observeForever(facultyListObserver)
        DataRepository.getInstatnce().faculty.observeForever {
            _faculty=it
        }
    }

    fun deleteFaculty(){
        if (faculty!=null)
            DataRepository.getInstatnce().deleteFaculty(faculty!!)
    }

    fun appendFaculty (facultyName : String, facultyCity: String){
        val faculty = Faculty()
        faculty.name=facultyName
        DataRepository.getInstatnce().newFaculty(faculty)
    }

    fun updateFaculty(facultyName: String, facultyCity: String){
        if (_faculty!=null) {
            _faculty!!.name=facultyName
            DataRepository.getInstatnce().updateFaculty(_faculty!!)
        }
    }

    fun setCurrentFaculty(faculty: Faculty){
        DataRepository.getInstatnce().setCurrentFaculty(faculty)
    }
}