package com.example.notesdemo.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Color
import android.util.Log
import com.example.notesdemo.model.NotesModel

class SqliteHelper(context: Context) : SQLiteOpenHelper(context , DATABASE_NAME , null , DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "NOTES_DEMO"

        const val DATABASE_VERSION = 1

        val TABLE_NAME = "Notes_Table5"
        val ID_COL = "id"
        val NOTE_CONTENT_COl = "note_content"
        val NOTE_IMAGE_COL = "note_image"
        val NOTE_TITLE_COL = "note_title"
        val NOTE_COLOR = "note_color"
        val TAG = "SqliteHelper"


    }

    override fun onCreate(db: SQLiteDatabase?) {

        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NOTE_TITLE_COL + " TEXT, " +
                NOTE_CONTENT_COl + " TEXT, " +
                NOTE_IMAGE_COL + " TEXT, " +
                NOTE_COLOR + " TEXT " + ")")

        db!!.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {

        // this method is to check if table already exists
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)

    }


    fun addNote(title: String, content: String) {
        // below we are creating a content values variable
        val values = ContentValues()

        // we are inserting our values in the form of key-value pair
        values.put(NOTE_TITLE_COL, title)
        values.put(NOTE_CONTENT_COl, content)
//        values.put(NOTE_IMAGE_COL, image)
//        values.put(NOTE_COLOR,color)

        // here we are creating a writable variable of our database as we want to insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        Log.e(TAG, "" + values)

        // at last we are closing our database
        db.close()

    }

    fun readNotes(): ArrayList<NotesModel> {
        // on below line we are creating a database for reading database.
        val db = this.readableDatabase

        // on below line we are creating a cursor with query to read data from database.
        val cursorCourses = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        Log.e("Error", "error in read notes" + cursorCourses)

        val courseModalArrayList: ArrayList<NotesModel> = ArrayList()

        // moving cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                courseModalArrayList.add(
                    NotesModel(
                        cursorCourses.getString(0),
                        cursorCourses.getString(1),
                        cursorCourses.getString(2)
//                        cursorCourses.getString(3),
//                        cursorCourses.getString(4),
                    )
                )
            } while (cursorCourses.moveToNext())
            // moving cursor to next.
        }

        cursorCourses.close()
        return courseModalArrayList
    }

    fun updateNotes(user: NotesModel) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NOTE_TITLE_COL, user.Note_title)
        values.put(NOTE_CONTENT_COl, user.Note_content)
//        values.put(NOTE_IMAGE_COL, user.Note_image)
//        values.put(NOTE_COLOR, user.Note_color)

        // Update the row in the database with the new values.
        db.update(TABLE_NAME, values, "$ID_COL=?", arrayOf(user.Note_id))

        // Close the database connection.
        db.close()
    }

    fun deleteCourse(note_id: String) {

        val db = this.writableDatabase

        db.delete(TABLE_NAME, "id=?", arrayOf(note_id))
        db.close()
    }

}
