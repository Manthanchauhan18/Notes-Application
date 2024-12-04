package com.example.notesdemo.view

import android.app.*
import android.content.*
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.databinding.DataBindingUtil
import com.example.notesdemo.R
import com.example.notesdemo.databinding.ActivityAddNotePageBinding
import com.example.notesdemo.helper.SqliteHelper
import com.example.notesdemo.model.NotesModel
import com.example.notesdemo.utils.DeleteNotification
import com.example.notesdemo.view.viewmodel.AddNoteViewModel
import com.example.notesdemo.view.viewmodel.UpdateNoteViewModel
import com.google.gson.JsonObject
import org.w3c.dom.Text


class AddNotePage : AppCompatActivity(), View.OnClickListener {

    private var note_content = ""
    private var note_title = ""
    private var note_id = ""
    private var from_item = false
    private lateinit var binding: ActivityAddNotePageBinding
    val db = SqliteHelper(this@AddNotePage)
     var title: String = ""
     var content: String = ""

    var editMode = 0
    var color: Int = -1

    val addNoteViewModel = AddNoteViewModel()
    val updateNoteViewModel = UpdateNoteViewModel()
    val TAG = "AddNotePage"

    lateinit var androidId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this@AddNotePage , R.layout.activity_add_note_page)
        init()
        setOnClickListener()
    }

    private fun init(){
        binding.apply {

            androidId = getAndroidId(this@AddNotePage)
            Log.e("AndroidID", "Android ID: $androidId")

            imageEdit.visibility = View.GONE
            imageSave.visibility = View.VISIBLE

            if(editMode == 0){
                txtTitle.isEnabled = true
                txtNote.isEnabled = true
            }else{
                txtTitle.isEnabled = false
                txtNote.isEnabled = false
            }
            from_item = intent.getBooleanExtra("from_item",false)
            note_id = intent.getStringExtra("note_id").toString()
            note_title = intent.getStringExtra("note_title").toString()
            note_content = intent.getStringExtra("note_content").toString()

            if (from_item) {
                binding.apply {

                    editMode = 1
                    txtTitle.isEnabled = false
                    txtNote.isEnabled = false

                    imageSave.visibility = View.GONE
                    imageEdit.visibility = View.VISIBLE

                    txtTitle.setText(note_title)

                    if(!note_content.equals("")){
                        txtNote.setText(note_content)
                    }
//
                    imageSave.setOnClickListener(){

                        val save_changes_dialog = Dialog(this@AddNotePage)
                        save_changes_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                        save_changes_dialog.setContentView(R.layout.save_changes_dialog)
                        save_changes_dialog.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        save_changes_dialog.getWindow()!!.setBackgroundDrawable(
                            ColorDrawable(Color.TRANSPARENT)
                        );
                        save_changes_dialog.setCancelable(false)
                        save_changes_dialog.getWindow()!!.setGravity(Gravity.CENTER);
                        save_changes_dialog.show()


                        val cancel: Button
                        val btnContinue: Button
                        cancel = save_changes_dialog.findViewById<Button>(R.id.btn_cancel)
                        btnContinue = save_changes_dialog.findViewById<Button>(R.id.btn_save)

                        cancel.setOnClickListener{

                            save_changes_dialog.dismiss()

                            val discard_changes_dialog = Dialog(this@AddNotePage)
                            discard_changes_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                            discard_changes_dialog.setContentView(R.layout.discard_changes_dialog)
                            discard_changes_dialog.getWindow()!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            discard_changes_dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
                            discard_changes_dialog.setCancelable(false)
                            discard_changes_dialog.getWindow()!!.setGravity(Gravity.CENTER);
                            discard_changes_dialog.show()


                            val cancelChild: Button
                            val btnContinueChild: Button
                            cancelChild = discard_changes_dialog.findViewById<Button>(R.id.btn_cancel)
                            btnContinueChild = discard_changes_dialog.findViewById<Button>(R.id.btn_save)

                            btnContinueChild.setOnClickListener{
                                discard_changes_dialog.dismiss()
                            }
                            cancelChild.setOnClickListener{
                                val intent_note_home = Intent(this@AddNotePage, NotesHome::class.java)
                                startActivity(intent_note_home)
                                finish()
                            }

                        }
                        btnContinue.setOnClickListener{

                            if (!txtNote.text.toString().equals("") ) {

                                txtTitle.clearFocus()
                                txtNote.clearFocus()

                                val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                                imm.hideSoftInputFromWindow(binding.root.getWindowToken(), 0)

                                title = txtTitle.text.toString()
                                content = txtNote.text.toString()

                                txtTitle.clearFocus()
                                txtNote.clearFocus()

                                Log.e("Error", "inside else")

                                val updated_title = txtTitle.text.toString()
                                val updated_content = txtNote.text.toString()
//                                val updated_imageUrl = imageUri.toString()
//                                val updated_color = color.toString()

                                // update the value in database
//                                val updateNote = NotesModel(note_id!!,updated_title, updated_content)
//                                db.updateNotes(updateNote)

                                // update the value in api
                                val jsonObject = JsonObject()
                                jsonObject.addProperty("note_title" , txtTitle.text.toString())
                                jsonObject.addProperty("note_message" , txtNote.text.toString())
                                Log.e(TAG, "init: note_id: ${androidId} , ${note_id} , ${jsonObject}", )
                                updateNoteViewModel.updateNote(androidId, note_id, jsonObject).observe(this@AddNotePage){
                                    Log.e(TAG, "init: $it", )
                                }

//                                showNotification(this@AddNotePage,0 , updated_title , updated_content)


                                val intent_note_home = Intent(this@AddNotePage, NotesHome::class.java)
                                startActivity(intent_note_home)
                                finish()


                            } else {
                                Toast.makeText(
                                    this@AddNotePage,
                                    "Please fill the note",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        }


                    }
//
//
                }

            }else{

                imageSave.setOnClickListener() {

                    imageSave.visibility = View.VISIBLE
                    imageEdit.visibility = View.GONE

                    if (!txtTitle.text.toString().equals("")) {

                        txtTitle.clearFocus()
                        txtNote.clearFocus()

                        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding.root.getWindowToken(), 0)

                        title = txtTitle.text.toString()
                        content = txtNote.text.toString()


//                        adding into local database
//                        db.addNote(title, content)

                        //adding into api
                        val jsonObject = JsonObject()
                        jsonObject.addProperty("note_title" , txtTitle.text.toString())
                        jsonObject.addProperty("note_message" , txtNote.text.toString())
                        addNoteViewModel.setNotes(androidId, jsonObject).observe(this@AddNotePage){
                            Log.e(TAG, "init: $it", )
                        }


                        Toast.makeText(this@AddNotePage, title + " added to database", Toast.LENGTH_LONG).show()

                        txtNote.text.clear()
                        txtTitle.text.clear()

//                        showNotification(this@AddNotePage,1 , title , content)

                        val intent_note_home = Intent(this@AddNotePage, NotesHome::class.java)
                        startActivity(intent_note_home)
                        finish()


                    } else {
                        Toast.makeText(this@AddNotePage, "Please fill the note", Toast.LENGTH_LONG).show()
                    }

                }


            }

        }
    }

    fun getAndroidId(context: Context): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    private fun setOnClickListener() {
        binding.apply {

            constraintAddNotes.setOnClickListener(this@AddNotePage)
            imgBack.setOnClickListener(this@AddNotePage)
            imageEdit.setOnClickListener(this@AddNotePage)

        }
    }

    override fun onClick(v: View?) {
        binding.apply {

            when(v!!.id){
                R.id.const_note_home_main -> {
                    txtTitle.clearFocus()
                    txtNote.clearFocus()

                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.getWindowToken(), 0)
                }
                R.id.img_back -> {
                    navigateUpToParentActivity()
                }
                R.id.image_edit -> {
                    editMode = 0
                    imageSave.visibility = View.VISIBLE
                    imageEdit.visibility = View.GONE

                    txtTitle.isEnabled = true
                    txtNote.isEnabled = true
                }

            }

        }
    }

//    private fun showNotification(context: Context,digit: Int , title: String  , content: String) {
//        val channel_id = "my_note"
//        val notificaton_id = 1
//        var text = ""
//
//        val dismissIntent = Intent(context, DeleteNotification::class.java)
//        dismissIntent.action = "ACTION_DISMISS"
//
//        val dismissPendingIntent = PendingIntent.getActivity(applicationContext, 1, dismissIntent,
//            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT // setting the mutability flag
//        )
//
//        val notificationBuilder = NotificationCompat.Builder(context , channel_id)
//            .setSmallIcon(R.mipmap.notes_icon)
//            .setContentTitle("${if (digit == 1) "New Note Added" else "Note Updated"}")
//            .setContentText(title)
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//            .addAction(R.drawable.ic_launcher_background, "Cancel", dismissPendingIntent)
//            .setAutoCancel(true)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = "My Expanse"
//            val descriptionText = "Channel Description"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channel_id, name, importance).apply {
//                description = descriptionText
//            }
//
//            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        val notificationManagerCompat = NotificationManagerCompat.from(context)
//        notificationManagerCompat.notify(notificaton_id, notificationBuilder.build())
//
//    }


    override fun onBackPressed() {
        super.onBackPressed()
        navigateUpToParentActivity()
        editMode = 0
    }

    private fun navigateUpToParentActivity() {
        val parentIntent = supportParentActivityIntent
        if (parentIntent == null) {
            // If the parent intent is null, simply call finish to close the current activity.
            finish()
        } else {
            // If the parent intent is not null, navigate up to the parent activity.
            startActivity(parentIntent)
            finish()
        }
    }


}