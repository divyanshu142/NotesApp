package com.example.notesapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.API.UserAPI
import com.example.notesapp.Utils.NetworkResult
import com.example.notesapp.modal.UserRequest
import com.example.notesapp.modal.UserResponce
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userAPI: UserAPI){

 private val _userResponselivedata = MutableLiveData<NetworkResult<UserResponce>>()
         val userResponselivedata : LiveData<NetworkResult<UserResponce>>
         get() = _userResponselivedata

    suspend fun registerUser(userRequest: UserRequest){
        _userResponselivedata.postValue(NetworkResult.Loading())
        val response = userAPI.signup(userRequest)
        handelResponse(response)
    }

    suspend fun loginUser(userRequest: UserRequest){
        _userResponselivedata.postValue(NetworkResult.Loading())
        val response = userAPI.signin(userRequest)
        handelResponse(response)
    }

    private fun handelResponse(response: Response<UserResponce>) {
        if (response.isSuccessful && response.body() != null) {
            _userResponselivedata.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            _userResponselivedata.postValue(NetworkResult.Error(error.getString("message")))
        } else {
            _userResponselivedata.postValue(NetworkResult.Error("Somthing went wrong wrong"))
        }
    }




}