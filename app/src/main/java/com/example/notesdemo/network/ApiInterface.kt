package com.example.notesdemo.network


import com.example.notesdemo.model.notes.Note
import com.google.gson.JsonObject
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiInterface {

    //used local node.js code
    @GET("note")
    fun getNotes(): Observable<Note>

    @POST("note")
    fun setNote(
        @Body jsonObject: JsonObject
    ): Observable<Note>

}