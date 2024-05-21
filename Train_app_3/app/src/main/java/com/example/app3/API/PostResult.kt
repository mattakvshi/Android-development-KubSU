package com.example.app3.API

import com.example.app3.data.University
import com.google.gson.annotations.SerializedName

class PostResult {
    @SerializedName("result") lateinit var resultString: University
}