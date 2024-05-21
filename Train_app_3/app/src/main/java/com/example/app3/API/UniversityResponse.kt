package com.example.app3.API

import com.example.app3.data.University
import com.google.gson.annotations.SerializedName

class UniversityResponse {
    @SerializedName("items") lateinit var items: List<University>
}