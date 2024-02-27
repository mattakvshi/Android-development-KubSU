package com.example.lessons3.data

import java.util.UUID


data class University (
    var id : UUID = UUID.randomUUID(),
    var name : String = "",
    var city : String = ""
)