package com.example.notesapp

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Repository.UserRepository
import com.example.notesapp.Utils.NetworkResult
import com.example.notesapp.modal.UserRequest
import com.example.notesapp.modal.UserResponce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    val UserResponseLiveData : LiveData<NetworkResult<UserResponce>>
    get() = userRepository.userResponselivedata

    fun registerUser (userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.registerUser(userRequest)

        }
    }

    fun loginUser (userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.loginUser(userRequest)
        }
    }

    fun validatecradiencal(username : String, password : String, email: String, islogin : Boolean) : Pair<Boolean, String>{

        var result = Pair(true, "")
        if(!islogin && TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(email)){
             result = Pair(false, "Please Provide The Credentials")
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            result = Pair(false, "Please Provide valid Email")
        }else if(password.length > 8){
            result = Pair(false, "Password Length Should Be Less Than 9")
        }else if(password.length < 4){
            result = Pair(false, "Password Length Should Be Greater Than 3")
        }
      return result
    }
}