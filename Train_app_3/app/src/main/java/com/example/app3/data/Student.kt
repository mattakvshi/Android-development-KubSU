package com.example.app3.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity(tableName = "students",
    indices = [Index("id"), Index("group_id", "id")],
    foreignKeys = [
        ForeignKey(
            entity = Group::class,
            parentColumns = ["id"],
            childColumns = ["group_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class Student(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var lastName: String = "",
    var firstName: String = "",
    var middleName: String = "",
    @ColumnInfo(name = "birth_date") var birthDate: Date = Date(),
    @ColumnInfo(name = "group_id") var groupID: UUID? = null,
    var phone: String = "",
    var sex: Int = 0
)
{
    val shortName
        get()=lastName+
                (if(firstName.isNotBlank()) {"${firstName.subSequence(0, 1)}."} else "") +
                (if(middleName.isNotBlank()) {"${middleName.subSequence(0, 1)}."} else "")
}
