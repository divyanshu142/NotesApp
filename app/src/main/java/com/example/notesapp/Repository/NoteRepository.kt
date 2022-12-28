package com.example.notesapp.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.notesapp.API.NotesAPI
import com.example.notesapp.Utils.NetworkResult
import com.example.notesapp.modal.NoteRequest
import com.example.notesapp.modal.NoteResponce
import com.example.notesapp.modal.UserResponce
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class NoteRepository @Inject constructor(private val notesAPI: NotesAPI) {

    private val _noteResponselivedata = MutableLiveData<NetworkResult<List<NoteResponce>>>()
    val noteResponselivedata : LiveData<NetworkResult<List<NoteResponce>>>
    get() = _noteResponselivedata

    private val _stutesLiveData = MutableLiveData<NetworkResult<String>>()
    val stutesLiveData : LiveData<NetworkResult<String>>
        get() = _stutesLiveData

    suspend fun GetNote(){
        _noteResponselivedata.postValue(NetworkResult.Loading())
        val response = notesAPI.getNotes()
        if (response.isSuccessful && response.body() != null) {
            _noteResponselivedata.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            val error = JSONObject(response.errorBody()!!.charStream().readText())
            _noteResponselivedata.postValue(NetworkResult.Error(error.getString("message")))
        } else {
            _noteResponselivedata.postValue(NetworkResult.Error("Somthing went wrong wrong"))
        }

    }

    suspend fun CreateNote(noteRequest: NoteRequest){

        _stutesLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.createNote(noteRequest)
        HendelResponce(response,  "Note Created")

    }


    suspend fun UpdateNote(noteId: String, noteRequest: NoteRequest){
        _stutesLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.updateNote(noteId, noteRequest)
        HendelResponce(response, "Note Updated")

    }
    suspend fun DeleteNote(noteId: String){
        _stutesLiveData.postValue(NetworkResult.Loading())
        val response = notesAPI.deleteNote(noteId)
        HendelResponce(response, "Note Deleted")

    }

    private fun HendelResponce(response: Response<NoteResponce>, massage: String) {
        if (response.isSuccessful && response.body() != null) {
            _stutesLiveData.postValue(NetworkResult.Success(massage))
        } else {
            _stutesLiveData.postValue(NetworkResult.Error("Somthing went wrong"))
        }
    }

}