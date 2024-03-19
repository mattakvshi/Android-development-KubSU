package com.example.lessons3.data

import java.util.UUID

data class Faculty(
    val id : UUID = UUID.randomUUID(),
    var name : String = "",
    var universityID: UUID?=null
)
