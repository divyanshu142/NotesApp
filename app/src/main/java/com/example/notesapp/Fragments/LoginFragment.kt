package com.example.notesapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notesapp.AuthViewModel
import com.example.notesapp.R
import com.example.notesapp.Utils.NetworkResult
import com.example.notesapp.Utils.TokenManager
import com.example.notesapp.databinding.FragmentLoginBinding
import com.example.notesapp.modal.UserRequest
import com.example.notesapp.modal.UserResponce
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater,container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.Loginuser.setOnClickListener {

            val ValidationResult = validateInput()
            if(ValidationResult.first){
                authViewModel.loginUser(GetUserRequest())
            }else{
                binding.errormsg.text = ValidationResult.second
            }
        }

        binding.Signupuser.setOnClickListener {

            findNavController().popBackStack()
        }

        BindObservers()
    }

    private fun GetUserRequest() : UserRequest{
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        return UserRequest(email, password, "")
    }

    private fun validateInput(): Pair<Boolean, String> {

        val userRequest = GetUserRequest()
        return authViewModel.validatecradiencal(userRequest.username,userRequest.password,userRequest.email, true)

    }

    private fun BindObservers() {

        authViewModel.UserResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it) {
                is NetworkResult.Success -> {
                    tokenManager.SaveToken(it.data!!.token)
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                is NetworkResult.Error -> {
                    binding.errormsg.text = it.massage
                }
                is NetworkResult.Loading -> {
                    binding.progressBar.isVisible = true
                }
             }
          })
      }

    override fun onDestroyView() {
        super.onDestroyView()
             _binding = null
    }
}