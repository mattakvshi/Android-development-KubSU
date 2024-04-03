package com.example.lessons3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.lessons3.data.Faculty
import com.example.lessons3.data.University


@Database(
    entities = [University::class, Faculty::class],
    version = 1,
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


        /*
    * val MIGRATION_2_3 = object : Migration(2, 3){
    *   override fun migrate(database: SupportSQLLiteDatabase){
    *   ...
    *
    *   }
    * }
    *
    * */


        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            UniversityDB::class.java,
            "university_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}