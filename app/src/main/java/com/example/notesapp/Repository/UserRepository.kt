package com.example.notesapp.Repository

import android.util.Log
import com.example.notesapp.API.UserAPI
import com.example.notesapp.Utils.Constants.TAG
import com.example.notesapp.modal.UserRequest
import javax.inject.Inject

class UserRepository @Inject constructor (private val userAPI: UserAPI){

    suspend fun RegisterUser(userRequest: UserRequest){
        val response = userAPI.signup(userRequest)
            Log.d(TAG, response.body().toString())

    }

    suspend fun LoginUser(userRequest: UserRequest){
        val response = userAPI.signin(userRequest)
        Log.d(TAG, response.body().toString())

    }
}