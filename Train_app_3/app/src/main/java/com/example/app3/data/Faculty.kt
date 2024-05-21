package com.example.app3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "faculties",
    indices = [Index("id"), Index("university_id")],
    foreignKeys = [
        ForeignKey(
            entity = University::class,
            parentColumns = ["id"],
            childColumns = ["university_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Faculty(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @ColumnInfo(name = "faculty_name") var name: String = "",
    @ColumnInfo(name = "university_id") var universityID: UUID? = null
)
