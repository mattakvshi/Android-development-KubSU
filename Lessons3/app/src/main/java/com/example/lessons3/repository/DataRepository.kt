package com.example.lessons3.repository

import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.preference.PreferenceManager
import com.example.lessons3.Application34
import com.example.lessons3.R
import com.example.lessons3.data.Faculty
import com.example.lessons3.data.FacultyList
import com.example.lessons3.data.Group
import com.example.lessons3.data.Student
import com.example.lessons3.data.University
import com.example.lessons3.data.UniversityList
import com.example.lessons3.database.UniversityDB
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

const val TAG = "com.example.lessons.log.tag.DataRepository"

class DataRepository private constructor() {

    companion object {
        private var INSTANS: DataRepository? = null

        fun getInstatnce(): DataRepository {
            if (INSTANS == null) {
                INSTANS = DataRepository()
            }
            return INSTANS
                ?: throw IllegalStateException("DataRepository Репазиторий не инициализирован")
        }
    }

    //var universityList: MutableLiveData<UniversityList?> = MutableLiveData()
    var university: MutableLiveData<University> = MutableLiveData()

//    fun newUniversity(university: University) {
//        val listTmp = (universityList.value ?: UniversityList()).apply {
//            items.add(university)
//        }
//
//        // Добавляем так чтобы сообщить о том что добавили
//        universityList.postValue(listTmp)
//        setCurrentUniversity(university)
//    }

    fun setCurrentUniversity(_university: University) {
        university.postValue(_university)
    }

    fun setCurrentUniversity(position: Int) {
        if (university.value == null || position < 0 || (universityList.value?.size!! <= position))
            return
        setCurrentUniversity(universityList.value!![position])
    }

    fun getUniversityPosition(university: University): Int =
        universityList.value?.indexOfFirst {
            it.id == university.id
        } ?: -1

    fun getUniversityPosition() = getUniversityPosition(university.value ?: University())

//    fun updateUniversity(university: University) {
//        val position = getUniversityPosition(university)
//        if (position < 0) newUniversity(university)
//        else {
//            val listTmp = universityList.value!!
//            listTmp.items[position] = university
//            universityList.postValue(listTmp)
//        }
//    }
//
//    fun deleteUniversity(university: University) {
//        val listTmp = universityList.value!!
//        if (listTmp.items.remove(university)) {
//            universityList.postValue(listTmp)
//        }
//        setCurrentUniversity(0)
//    }

//    fun saveData() {
//        val context = Application34.context
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        sharedPreferences.edit().apply {
//            val gson = Gson()
//            val lst = universityList.value ?: listOf<University>()
//            val jsonString = gson.toJson(lst)
//            Log.d(TAG, "Сохранение $jsonString")
//            putString(context.getString(R.string.preference_key_university_list), jsonString)
//
//            putString(
//                context.getString(R.string.preference_key_faculty_list),
//                gson.toJson(facultyList.value ?: listOf<Faculty>())
//            )
//
//            apply()
//        }
//    }

//    fun loadData() {
//        val context = Application34.context
//        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//        sharedPreferences.apply {
//            var jsonString =
//                getString(context.getString(R.string.preference_key_university_list), null)
//            if (jsonString != null) {
//                Log.d(TAG, "Чтение $jsonString")
//                val listType = object : TypeToken<List<University>>() {}.type
//                val tempList = Gson().fromJson<List<University>>(jsonString, listType)
//                val temp = UniversityList()
//                temp.items = tempList.toMutableList()
//                Log.d(TAG, "Загрузка ${temp.toString()}")
//                universityList.value = temp
//                setCurrentUniversity(0)
//            }
//
//
//            jsonString = getString(context.getString(R.string.preference_key_faculty_list), null)
//            if (jsonString != null) {
//                Log.d(TAG, "Чтение $jsonString")
//                val listType = object : TypeToken<List<Faculty>>() {}.type
//                val tempList = Gson().fromJson<List<Faculty>>(jsonString, listType)
//                val temp = FacultyList()
//                temp.items = tempList.toMutableList()
//                Log.d(TAG, "Загрузка ${temp.toString()}")
//                facultyList.value = temp
//                setCurrentFaculty(0)
//            }
//        }
//    }


    //var facultyList: MutableLiveData<FacultyList?> = MutableLiveData()
    var faculty: MutableLiveData<Faculty> = MutableLiveData()

//    fun newFaculty(faculty: Faculty) {
//        val listTmp = (facultyList.value ?: FacultyList()).apply {
//            items.add(faculty)
//        }
//
//        // Добавляем так чтобы сообщить о том что добавили
//        facultyList.postValue(listTmp)
//        setCurrentFaculty(faculty)
//    }

    fun setCurrentFaculty(_faculty: Faculty) {
        faculty.postValue(_faculty)
    }

    fun setCurrentFaculty(position: Int) {
        if (faculty.value == null || position < 0 || (facultyList.value?.size!! <= position))
            return
        setCurrentFaculty(facultyList.value!![position])
    }

    fun getFacultyPosition(faculty: Faculty): Int = facultyList.value?.indexOfFirst {
        it.id == faculty.id
    } ?: -1

    fun getFacultyPosition() = getFacultyPosition(faculty.value ?: Faculty())

//    fun updateFaculty(faculty: Faculty) {
//        val position = getFacultyPosition(faculty)
//        if (position < 0) newFaculty(faculty)
//        else {
//            val listTmp = facultyList.value!!
//            listTmp.items[position] = faculty
//            facultyList.postValue(listTmp)
//        }
//    }
//
//    fun deleteFaculty(faculty: Faculty) {
//        val listTmp = facultyList.value!!
//        if (listTmp.items.remove(faculty)) {
//            facultyList.postValue(listTmp)
//        }
//        setCurrentFaculty(0)
//    }

    private val universityDB by lazy {
        DBRepository(UniversityDB.getDatabase(Application34.context).universityDAO())
    }

    private val myCoroutineScope = CoroutineScope(Dispatchers.Main)

    fun onDestroy(){
        myCoroutineScope.cancel()
    }

    val universityList: LiveData<List<University>> = universityDB.getUniversities().asLiveData()

    fun newUniversity(university: University){
        myCoroutineScope.launch {
            universityDB.insertUniversity(university)
            setCurrentUniversity(university)
        }
    }

    fun updateUniversity(university: University){
        myCoroutineScope.launch {
            universityDB.updateUniversity(university)
        }
    }

    fun deleteUniversity(university: University){
        myCoroutineScope.launch {
            universityDB.deleteUniversity(university)
            setCurrentUniversity(0)
        }
    }

    val facultyList: LiveData<List<Faculty>> = universityDB.getFaculties().asLiveData()

    fun newFaculty(faculty: Faculty){
        myCoroutineScope.launch {
            universityDB.insertFaculty(faculty)
            setCurrentFaculty(faculty)
        }
    }

    fun updateFaculty(faculty: Faculty){
        newFaculty(faculty)
    }

    fun deleteFaculty(faculty: Faculty){
        myCoroutineScope.launch {
            universityDB.deleteFaculty(faculty)
            setCurrentFaculty(0)
        }
    }


    val listOfGroup: LiveData<List<Group>> = universityDB.getAllGroups().asLiveData()
    var listOfStudent: LiveData<List<Student>> = universityDB.getAllStudents().asLiveData()
    var group: MutableLiveData<Group> = MutableLiveData()
    var student: MutableLiveData<Student> = MutableLiveData()

    fun getGroupPosition(group: Group): Int = listOfGroup.value?.indexOfFirst {
        it.id == group.id
    } ?: -1

    fun getGroupPosition() = getGroupPosition(group.value?: Group())

    fun setCurrentGroup(position: Int) {
        if(listOfGroup.value == null || position < 0 || (listOfGroup.value?.size!! <= position))
            return
        setCurrentGroup(listOfGroup.value!![position])
    }

    fun setCurrentGroup(_group: Group) {
        group.postValue(_group)
    }

    fun addGroup(group: Group) {

    }
}