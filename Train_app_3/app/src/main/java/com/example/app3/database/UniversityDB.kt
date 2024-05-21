package com.example.app3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.app3.data.Faculty
import com.example.app3.data.Group
import com.example.app3.data.Student
import com.example.app3.data.University

@Database(
    entities = [University::class, Faculty::class, Group::class, Student::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(UniversityTypeConverter::class)

abstract class UniversityDB: RoomDatabase() {

    abstract fun universityDAO(): UniversityDAO

    companion object {
        @Volatile
        private var INSTANCE: UniversityDB? = null

        fun getDatabase(context: Context): UniversityDB {
            return INSTANCE ?: synchronized(this) {
                buildDatabase(context).also { INSTANCE = it }
            }
        }

        /*val MIGRATION_2_3 = object: Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                ...
            }
        }*/

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            UniversityDB::class.java,
            "university_database"
        )
            .fallbackToDestructiveMigration()
            //.addMigrations(MIGRATION_2_3)
            .build()
    }

}