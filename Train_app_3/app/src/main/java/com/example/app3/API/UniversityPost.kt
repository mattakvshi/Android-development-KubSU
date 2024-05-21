package com.example.app3.API

import com.example.app3.data.University
import com.google.gson.annotations.SerializedName

class UniversityPost (
    @SerializedName("action") val action: Int,
    @SerializedName("university") val university: University

//    "action": 1,
//    "university": [
//        "id": "cguwvhioijpokc=hvhwojewvewm",
//        "name": "wohiew",
//        "city": "jcenwlkv"
//    ]
)