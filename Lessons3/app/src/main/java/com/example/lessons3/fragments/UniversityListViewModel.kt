package com.example.lessons3.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.lessons3.data.University
import com.example.lessons3.data.UniversityList
import com.example.lessons3.repository.DataRepository

class UniversityListViewModel : ViewModel() {
    var universityList: MutableLiveData<UniversityList?>  = MutableLiveData()

    private var _university: University? = null

    val university
        get()=_university

    private val universityListObserver = Observer<UniversityList?>{
        list ->
        universityList.postValue(list)
    }

    init{
        DataRepository.getInstatnce().universityList.observeForever(universityListObserver)
        DataRepository.getInstatnce().university.observeForever {
            _university=it
        }
    }

    fun deleteUniversity(){
        if (university!=null)
            DataRepository.getInstatnce().deleteUniversity(university!!)
    }

    fun appendUniversity (universityName : String, universityCity: String){
        val university = University()
        university.name=universityName
        university.city=universityCity
        DataRepository.getInstatnce().newUniversity(university)
    }

    fun updateUniversity(universityName: String, universityCity: String){
        if (_university!=null) {
            _university!!.name=universityName
            _university!!.city=universityCity
            DataRepository.getInstatnce().updateUniversity(_university!!)
        }
    }

    fun setCurrentUniversity(university: University){
        DataRepository.getInstatnce().setCurrentUniversity(university)
    }

}