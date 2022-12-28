package com.example.notesapp.API

import com.example.notesapp.modal.NoteRequest
import com.example.notesapp.modal.NoteResponce
import retrofit2.Response
import retrofit2.http.*

interface NotesAPI {

    @GET("/note")
    suspend fun getNotes(): Response<List<NoteResponce>>

    @POST("/note")
    suspend fun createNote(@Body noteRequest: NoteRequest): Response<NoteResponce>

    @PUT("/note/{noteId}")
    suspend fun updateNote(@Path("noteId") noteId: String, @Body noteRequest: NoteRequest): Response<NoteResponce>

    @DELETE("/note/{noteId}")
    suspend fun deleteNote(@Path("noteId") noteId: String): Response<NoteResponce>

}