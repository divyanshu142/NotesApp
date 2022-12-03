package com.example.notesapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesapp.AuthViewModel
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentRagistrationBinding
import com.example.notesapp.modal.UserRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RagistrationFragment : Fragment() {

    private var _binding : FragmentRagistrationBinding? = null
    private val binding get() = _binding!!
    private val authViewModel by viewModels<AuthViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRagistrationBinding.inflate(inflater,container, false)

        binding.button.setOnClickListener {
            //findNavController().navigate(R.id.action_ragistrationFragment_to_mainFragment)
            authViewModel.registerUser(UserRequest("test00@gmail.com", "111111", "test0011" ))

        }

        binding.loginuser.setOnClickListener{
           // findNavController().navigate(R.id.action_ragistrationFragment_to_loginFragment)
            authViewModel.loginUser(UserRequest("test00@gmail.com", "111111", "test0011" ))

        }


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}