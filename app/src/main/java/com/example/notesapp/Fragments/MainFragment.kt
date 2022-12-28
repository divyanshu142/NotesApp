package com.example.notesapp.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notesapp.NoteAdapter
import com.example.notesapp.NoteViewModel
import com.example.notesapp.R
import com.example.notesapp.Utils.NetworkResult
import com.example.notesapp.databinding.FragmentMainBinding
import com.example.notesapp.modal.NoteResponce
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var Adepar: NoteAdapter

    private val noteViewModel by viewModels<NoteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater,container, false)
         Adepar = NoteAdapter(::onClicknote)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_noteFragment)
        }

        Observal()
        noteViewModel.getNote()
        binding.notelist.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.notelist.adapter = Adepar

    }

    private fun Observal() {
      noteViewModel.noteResponceLiveData.observe(viewLifecycleOwner, Observer {

          binding.progressBar.isVisible = false
          when(it){
              is NetworkResult.Success -> {
                  Adepar.submitList(it.data)
              }
              is NetworkResult.Error -> {
                  Toast.makeText(requireContext() ,it.massage.toString(), Toast.LENGTH_SHORT).show()
              }
              is NetworkResult.Loading -> {
                  binding.progressBar.isVisible = true
              }
           }
        })
    }

    fun onClicknote(responce: NoteResponce){

        val bundle = Bundle()
        bundle.putString("note", Gson().toJson(responce))
        findNavController().navigate(R.id.action_mainFragment_to_noteFragment, bundle)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

