package com.example.app3.repository

import com.example.app3.data.Faculty
import com.example.app3.data.Group
import com.example.app3.data.Student
import com.example.app3.data.University
import com.example.app3.database.UniversityDAO
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class DBRepository(val dao: UniversityDAO): UniversityDBRepository {

    override fun getUniversities(): Flow<List<University>> = dao.getUniversities()
    override suspend fun insertUniversity(university: University) = dao.insertUniversity(university)
    override suspend fun updateUniversity(university: University) = dao.updateUniversity(university)
    override suspend fun insertListUniversity(universityList: List<University>) = dao.insertListUniversity(universityList)
    override suspend fun deleteUniversity(university: University) = dao.deleteUniversity(university)
    override suspend fun deleteAllUniversity() = dao.deleteAllUniversity()

    override fun getAllFaculties(): Flow<List<Faculty>> = dao.getAllFaculties()
    override fun getUniversityFaculty(universityID: UUID): Flow<List<Faculty>> = dao.getUniversityFaculty(universityID)
    override suspend fun insertFaculty(faculty: Faculty) = dao.insertFaculty(faculty)
    override suspend fun deleteFaculty(faculty: Faculty) = dao.deleteFaculty(faculty)
    override suspend fun deleteUniversityFaculties(universityID: UUID) = dao.deleteUniversityFaculties(universityID)
    override suspend fun deleteAllFaculties() = dao.deleteAllFaculties()

    override fun getAllGroups(): Flow<List<Group>> = dao.getAllGroups()
    override fun getFacultyGroup(facultyID: UUID): Flow<List<Group>> = dao.getFacultyGroup(facultyID)
    override suspend fun insertGroup(group: Group) = dao.insertGroup(group)
    override suspend fun deleteGroup(group: Group) = dao.deleteGroup(group)
    override suspend fun deleteAllGroups() = dao.deleteAllGroups()

    override fun getAllStudents(): Flow<List<Student>> = dao.getAllStudents()
    override fun getGroupStudent(groupID: UUID): Flow<List<Student>> = dao.getGroupStudent(groupID)
    override suspend fun insertStudent(student: Student) = dao.insertStudent(student)
    override suspend fun deleteStudent(student: Student) = dao.deleteStudent(student)
    override suspend fun deleteAllStudents() = dao.deleteAllStudents()
}