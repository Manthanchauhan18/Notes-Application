package com.example.notesdemo.model.notes

class Note : ArrayList<NoteItem>()

data class NoteItem(
    val note_id: Int,
    val note_message: String,
    val note_title: String
)