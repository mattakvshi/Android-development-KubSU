package com.example.lessons3.repository

import com.example.lessons3.data.Faculty
import com.example.lessons3.data.University
import com.example.lessons3.database.UniversityDAO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DBRepository(val dao: UniversityDAO) :UniversityDBRepository{
    //University
    override fun getUniversities(): Flow<List<University>> = dao.getUniversities()

    override suspend fun insertUniversity(university: University) = dao.insertUniversity(university)

    override suspend fun updateUniversity(university: University) = dao.updateUniversity(university)

    override suspend fun insertListUniversity(universityList: List<University>) = dao.insertListUniversity(universityList)

    override suspend fun deleteUniversity(university: University) = dao.deleteUniversity(university)

    override suspend fun deleteAllUniversity() = dao.deleteAllUniversity()


    //Faculty


    override fun getFaculties(): Flow<List<Faculty>> = dao.getFaculties()

    override fun getUniversityFaculty(universityID: UUID): Flow<List<Faculty>> = dao.getUniversityFaculty(universityID)

    override suspend fun insertFaculty(faculty: Faculty) = dao.insertFaculty(faculty)

    override suspend fun updateFaculty(faculty: Faculty) = dao.updateFaculty(faculty)

    override suspend fun insertListFaculty(facultyList: List<Faculty>) = dao.insertListFaculty(facultyList)

    override  suspend fun deleteFaculty(faculty: Faculty) = dao.deleteFaculty(faculty)

    override suspend fun deleteUniversityFaculties(universityID: UUID) = dao.deleteUniversityFaculties(universityID)

    override suspend fun deleteAllFaculty() = dao.deleteAllFaculty()

}