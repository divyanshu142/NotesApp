package com.example.notesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notesapp.Repository.NoteRepository
import com.example.notesapp.Utils.NetworkResult
import com.example.notesapp.modal.NoteRequest
import com.example.notesapp.modal.NoteResponce
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(private val noteRepository: NoteRepository) : ViewModel(){

    val noteResponceLiveData get() = noteRepository.noteResponselivedata
    val stutesLiveData get() = noteRepository.stutesLiveData

    fun getNote(){
        viewModelScope.launch {
            noteRepository.GetNote()
        }
    }

    fun createNote(noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepository.CreateNote(noteRequest)
        }
    }

    fun updateNote(noteId : String, noteRequest: NoteRequest){
        viewModelScope.launch {
            noteRepository.UpdateNote(noteId, noteRequest)
        }
    }

    fun deleteNote(noteId : String){
        viewModelScope.launch {
            noteRepository.DeleteNote(noteId)
        }
    }
}