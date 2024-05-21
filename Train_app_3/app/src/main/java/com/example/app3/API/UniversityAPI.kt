package com.example.app3.API

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

const val GET_UNIVERSITY = 10
const val APPEND_UNIVERSITY = 11
const val UPDATE_UNIVERSITY = 12
const val DELETE_UNIVERSITY = 13

interface UniversityAPI {
    @GET("?code=$GET_UNIVERSITY")
    fun getUniversities(): Call<UniversityResponse>

    @Headers("Content-Type: application/json")
    @POST("university")
    fun postUniversity(@Body postUniversity: UniversityPost): Call<PostResult>
}