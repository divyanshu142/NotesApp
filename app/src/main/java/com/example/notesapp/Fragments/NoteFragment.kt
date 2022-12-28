package com.example.notesapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.notesapp.NoteViewModel
import com.example.notesapp.R
import com.example.notesapp.Utils.NetworkResult
import com.example.notesapp.databinding.FragmentMainBinding
import com.example.notesapp.databinding.FragmentNoteBinding
import com.example.notesapp.modal.NoteRequest
import com.example.notesapp.modal.NoteResponce
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private val noteViewModle by viewModels<NoteViewModel>()
    private var note: NoteResponce? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentNoteBinding.inflate(inflater,container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialdata()
        bindHendler()
        bindObserver()
    }

    private fun bindObserver() {
        noteViewModle.stutesLiveData.observe(viewLifecycleOwner, Observer {

            when(it){
                is NetworkResult.Success -> {
                    findNavController().popBackStack()
                }
                is NetworkResult.Error -> {

                }
                is NetworkResult.Loading -> {

                }
            }
        })
    }

    private fun bindHendler() {

        binding.delete.setOnClickListener {
            note?.let { noteViewModle.deleteNote(it._id) }
        }

        binding.btnsubmit.setOnClickListener {
            val title = binding.textitle.text.toString()
            val discription = binding.texdescription.text.toString()
            val noteRequest = NoteRequest(title, discription)
            if(note == null){
                noteViewModle.createNote(noteRequest)
            }else{
                noteViewModle.updateNote(note!!._id, noteRequest)
            }

            }
        }


    private fun setInitialdata() {

        val jsonNote = arguments?.getString("note")

        if(jsonNote != null ){
            note = Gson().fromJson(jsonNote, NoteResponce::class.java)
            note?.let {
                binding.textitle.setText(it.title)
                binding.texdescription.setText(it.description)
            }
        }else{
            binding.addnote.text = "ADD NOTE"
        }

        }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}