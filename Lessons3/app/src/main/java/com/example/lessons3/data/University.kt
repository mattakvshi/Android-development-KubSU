package com.example.lessons3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID


@Entity
    (
            indices = [Index("id")]
            )
data class University (
    @PrimaryKey var id : UUID = UUID.randomUUID(),
    @ColumnInfo(name = "university_name") var name : String = "",
    @ColumnInfo(name = "university_city")var city : String = ""
)