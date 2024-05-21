package com.example.app3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.util.UUID

@Entity(
    indices = [Index("id")]
)
data class University(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    @SerializedName("university_name") @ColumnInfo(name = "university_name") var name: String = "",
    var city: String = ""
)
