package com.example.app3.repository

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.app3.data.Faculty
import com.example.app3.data.Group
import com.example.app3.data.Student
import com.example.app3.data.University
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface UniversityDBRepository {

    fun getUniversities(): Flow<List<University>>
    suspend fun insertUniversity(university: University)
    suspend fun updateUniversity(university: University)
    suspend fun insertListUniversity(universityList: List<University>)
    suspend fun deleteUniversity(university: University)
    suspend fun deleteAllUniversity()

    fun getAllFaculties(): Flow<List<Faculty>>
    fun getUniversityFaculty(universityID: UUID): Flow<List<Faculty>>
    suspend fun insertFaculty(faculty: Faculty)
    suspend fun deleteFaculty(faculty: Faculty)
    suspend fun deleteUniversityFaculties(universityID: UUID)
    suspend fun deleteAllFaculties()

    //ниже - функции с последней пары

    fun getAllGroups(): Flow<List<Group>>
    fun getFacultyGroup(facultyID: UUID): Flow<List<Group>>
    suspend fun insertGroup(group: Group)
    suspend fun deleteGroup(group: Group)
    suspend fun deleteAllGroups()

    fun getAllStudents(): Flow<List<Student>>
    fun getGroupStudent(groupID: UUID): Flow<List<Student>>
    suspend fun insertStudent(student: Student)
    suspend fun deleteStudent(student: Student)
    suspend fun deleteAllStudents()
}