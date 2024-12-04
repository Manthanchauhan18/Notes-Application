package com.example.notesdemo.network

import com.example.notesdemo.model.NotesApi
import com.example.notesdemo.model.notes.Note
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.*

interface ApiInterface {

    //used local node.js code
    @GET("note")
    fun getNotes(
        @Query("androidId") androidId: String
    ): Observable<NotesApi>

    @POST("note/create")
    fun setNote(
        @Query("androidId") androidId: String,
        @Body jsonObject: JsonObject
    ): Observable<NotesApi>

    @PATCH("note/update/{id}")
    fun updateNote(
        @Path("id") id: String,
        @Query("androidId") androidId: String,
        @Body jsonObject: JsonObject
    ): Observable<NotesApi>

    @DELETE("note/delete/{id}")
    fun deleteNote(
        @Path("id") id: String,
        @Query("androidId") androidId: String
    ): Observable<NotesApi>

}