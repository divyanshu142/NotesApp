package com.example.notesapp.API

import com.example.notesapp.modal.UserRequest
import com.example.notesapp.modal.UserResponce
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserAPI {

    @POST( "/users/signup")
    suspend fun signup(@Body userRequest : UserRequest) : Response<UserResponce>

    @POST( "/users/signin")
    suspend fun signin(@Body userRequest: UserRequest) : Response<UserResponce>

}