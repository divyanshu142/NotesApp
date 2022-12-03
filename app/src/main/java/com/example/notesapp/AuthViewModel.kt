package com.example.notesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Repository.UserRepository
import com.example.notesapp.modal.UserRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor (private val userRepository: UserRepository) : ViewModel() {

    fun registerUser (userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.RegisterUser(userRequest)
        }
    }

    fun loginUser (userRequest: UserRequest){
        viewModelScope.launch {
            userRepository.LoginUser(userRequest)
        }
    }
}