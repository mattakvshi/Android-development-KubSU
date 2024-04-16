package com.example.lessons3.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.lessons3.data.Faculty
import com.example.lessons3.data.Group
import com.example.lessons3.data.Student
import com.example.lessons3.data.University
import com.example.lessons3.data.UniversityList
import kotlinx.coroutines.flow.Flow
import java.util.UUID


@Dao
interface UniversityDAO {
    
    //University

    @Query("SELECT * FROM University ORDER BY university_name")
    fun getUniversities(): Flow<List<University>>

    @Insert(entity = University::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUniversity(university: University)

    @Update(entity = University::class)
    suspend fun updateUniversity(university: University)

    @Insert(entity = University::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListUniversity(universityList: List<University>)

    @Delete(entity = University::class)
    suspend fun deleteUniversity(university: University)

    @Query("DELETE FROM University")
    suspend fun deleteAllUniversity()
    

    //Faculty


    @Query("SELECT * FROM faculties ")
    fun getFaculties(): Flow<List<Faculty>>

    @Query("SELECT * FROM faculties WHERE university_id = :universityID")
    fun getUniversityFaculty(universityID: UUID): Flow<List<Faculty>>

    @Insert(entity = Faculty::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFaculty(faculty: Faculty)

    @Update(entity = Faculty::class)
    suspend fun updateFaculty(faculty: Faculty)

    @Insert(entity = Faculty::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertListFaculty(facultyList: List<Faculty>)

    @Delete(entity = Faculty::class)
    suspend fun deleteFaculty(faculty: Faculty)

    @Query("DELETE FROM faculties WHERE university_id = :universityID")
    suspend fun deleteUniversityFaculties(universityID: UUID)

    @Query("DELETE FROM faculties")
    suspend fun deleteAllFaculty()


    //Group

    @Query("SELECT * FROM groups")
    fun getAllGroups(): Flow<List<Group>>

    @Query("SELECT * FROM groups WHERE faculty_id=:facultyID")
    fun getFacultyGroup(facultyID: UUID): Flow<List<Group>>

    @Insert(entity = Group::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGroup(group: Group)

    @Delete(entity = Group::class)
    suspend fun deleteGroup(group: Group)


    //Students

    @Query("DELETE FROM groups")
    suspend fun deleteAllGroups()

    @Query("SELECT * FROM students")
    fun getAllStudents(): Flow<List<Student>>

    @Query("SELECT * FROM students WHERE group_id=:groupID")
    fun getGroupStudent(groupID: UUID): Flow<List<Student>>

    @Insert(entity = Student::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Delete(entity = Student::class)
    suspend fun deleteStudent(student: Student)

    @Query("DELETE FROM students")
    suspend fun deleteAllStudents()
    
    
}