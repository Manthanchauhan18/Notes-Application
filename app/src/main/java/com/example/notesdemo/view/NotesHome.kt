package com.example.notesdemo.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.adapters.TextViewBindingAdapter.OnTextChanged
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.notesdemo.R
import com.example.notesdemo.databinding.ActivityNotesHomeBinding
import com.example.notesdemo.helper.SqliteHelper
import com.example.notesdemo.model.NotesModel
import com.example.notesdemo.model.notes.Note
import com.example.notesdemo.model.notes.NoteItem
import com.example.notesdemo.utils.SwipeGesture
import com.example.notesdemo.view.adapter.NoteListAdapter
import com.example.notesdemo.view.viewmodel.NoteViewmodel
import com.faltenreich.skeletonlayout.Skeleton
import com.google.gson.JsonObject
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

class NotesHome : AppCompatActivity() , NoteListAdapter.OnItemClickListener , View.OnClickListener {

    private lateinit var binding: ActivityNotesHomeBinding
    private lateinit var skeleton: Skeleton
    private lateinit var noteList: ArrayList<NotesModel>
    private lateinit var noteListAdapter: NoteListAdapter
    private lateinit var noteListHelper: SqliteHelper
    var doubleBackToExitPressedOnce = false
    val noteViewModel = NoteViewmodel()
    val TAG = "NotesHome"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this@NotesHome, R.layout.activity_notes_home) // DataBinding
        binding = ActivityNotesHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //used local node.js code
        init()
    }

    private fun init(){

        binding.apply {

            noteListAdapter = NoteListAdapter(this@NotesHome , this@NotesHome)

            skeleton = skeletonLayout
            skeleton.showSkeleton()
            setOnClickListener()
            noteList = ArrayList()
            loadData()

            swipeToRefresh.setOnRefreshListener {
                skeleton.showSkeleton()
                loadData()
                swipeToRefresh.isRefreshing = false
            }

            editSearchview.addTextChangedListener(object : OnTextChanged, TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                @SuppressLint("RestrictedApi")
                override fun onTextChanged(searchText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    filter(searchText.toString())
                }

                override fun afterTextChanged(p0: Editable?) {}

            })

        }

    }

    private fun setOnClickListener() {
        binding.apply {
            floatingbuttonAddNotesHome.setOnClickListener(this@NotesHome)
            constNoteHomeMain.setOnClickListener(this@NotesHome)
            imgSearch.setOnClickListener(this@NotesHome)
            imgClose.setOnClickListener(this@NotesHome)
        }
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v!!.id) {
                R.id.floatingbutton_add_notes_home -> {
                    val intent_add_note = Intent(this@NotesHome, AddNotePage::class.java)
                    startActivity(intent_add_note)
                }
                R.id.const_note_home_main -> {
                    editSearchview.clearFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding.root.getWindowToken(), 0)
                }
                R.id.img_search -> {
                    constSearchview.visibility = View.VISIBLE
                    constToolbarWithoutSearchview.visibility = View.GONE
                    editSearchview.requestFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(editSearchview ,  InputMethodManager.SHOW_IMPLICIT)
                }
                R.id.img_back -> {
                    searchviewClose()
                }
                R.id.img_close -> {
                    searchviewClose()
                }
            }

        }


    }

    private fun loadData() {
        binding.apply {

            //using local database

            noteListHelper = SqliteHelper(this@NotesHome)
            noteList = noteListHelper.readNotes() // Read data from the database

            if (noteList.isEmpty()) {
                emptyLottieAnimation.visibility = View.VISIBLE
                recyclerviewNotesHome.visibility = View.GONE
            } else {
                emptyLottieAnimation.visibility = View.GONE
                recyclerviewNotesHome.visibility = View.VISIBLE

                if(noteList.size == 1){
                    noteListAdapter.setList(noteList)
                    recyclerviewNotesHome.adapter = noteListAdapter
                }else{
                    val reverseList = noteList.reversed()
                    noteListAdapter.setList(reverseList as ArrayList<NotesModel>)
                    recyclerviewNotesHome.adapter = noteListAdapter
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    onDataLoaded()
                }, 1500)

            }


            //using api
//            noteViewModel.getNotes().observe(this@NotesHome){
//                Log.e(TAG, "loadData: $it", )
//                noteList.addAll(it)
//                if (noteList.isEmpty()) {
//                    emptyLottieAnimation.visibility = View.VISIBLE
//                    recyclerviewNotesHome.visibility = View.GONE
//                } else {
//                    emptyLottieAnimation.visibility = View.GONE
//                    recyclerviewNotesHome.visibility = View.VISIBLE
//
//                    if (noteList.size == 1) {
//                        noteListAdapter.setList(noteList)
//                        recyclerviewNotesHome.adapter = noteListAdapter
//                    } else {
//                        val reverseList = noteList.reversed()
//                        noteListAdapter.setList(reverseList as ArrayList<NoteItem>)
//                        recyclerviewNotesHome.adapter = noteListAdapter
//                    }
//
//                    Handler(Looper.getMainLooper()).postDelayed({
//                        onDataLoaded()
//                    }, 1500)
//                }
//            }

        }


    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<NotesModel> = ArrayList()

        for (item in noteList) {
            if (item.Note_title.toLowerCase().contains(text.toLowerCase()) || item.Note_content.toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item)
            }
        }

        if (filteredlist.isEmpty()) {
            binding.swipeToRefresh.visibility = View.GONE
            binding.emptyLottieAnimation.visibility = View.VISIBLE
        } else {
            noteListAdapter.setList(filteredlist)
            binding.swipeToRefresh.visibility = View.VISIBLE
            binding.emptyLottieAnimation.visibility = View.GONE

        }
    }

    private fun drag() {
        // Enable drag-and-drop
        val dragCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, // Enable drag up and down
            0
        ) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition

                // Swap items in your list
                Collections.swap(noteList, fromPosition, toPosition)

                // Notify the adapter of the move
                noteListAdapter.notifyItemMoved(fromPosition, toPosition)

                // You can also update the database if needed

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

        }

        val dragAndDropHelper = ItemTouchHelper(dragCallback)
        dragAndDropHelper.attachToRecyclerView(binding.recyclerviewNotesHome)
    }

//    private fun swipe() {
//        val swipeGesture = object : SwipeGesture(this@NotesHome){
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                when(direction){
//                    ItemTouchHelper.LEFT -> {
//                        val position = viewHolder.adapterPosition
//                        AlertDialog.Builder(this@NotesHome)
//                            .setTitle("Delete")
//                            .setIcon(R.drawable.notes_icon)
//                            .setMessage("Are you sure you want to Delete?")
//                            .setCancelable(false)
//                            .setPositiveButton("Yes") { dialog, whichButton ->
//
//                                val title = noteList[(noteList.size-1) - viewHolder.adapterPosition]
//
//                                val deletedCourse: NotesModel = noteList[(noteList.size-1) - viewHolder.adapterPosition]
//
//                                Log.e("TAG", "onSwiped:---> ${viewHolder.adapterPosition} , ${deletedCourse.Note_title} " )
//
//                                noteList.removeAt(viewHolder.adapterPosition)
//                                noteListAdapter.removeItem(viewHolder.adapterPosition)
////                                noteListAdapter.notifyItemRemoved(viewHolder.adapterPosition)
//
//                                GlobalScope.launch { noteListHelper.deleteCourse(deletedCourse.Note_id) }
//
//                                if(noteList.isEmpty()){
//                                    binding.emptyLottieAnimation.visibility = View.VISIBLE
//                                    binding.recyclerviewNotesHome.visibility = View.GONE
//                                }
//
////                            // below line is to display our snackbar with action.
////                            Snackbar.make(binding.recyclerviewNotesHome, "Deleted ", Snackbar.LENGTH_LONG)
////                                .setAction(
////                                "Undo",
////                                View.OnClickListener {
////                                    // adding on click listener to our action of snack bar.
////                                    // below line is to add our item to array list with a position.
////                                    noteList.add(position, deletedCourse)
////
////                                    GlobalScope.launch {
////                                        noteListHelper.addNote(deletedCourse.Note_title , deletedCourse.Note_content)
////                                    }
////                                    // below line is to notify item is
////
////                                    noteListAdapter.undoItem(noteList)
////                                }).show()
//                            }
//                            .setNegativeButton("No") { dialog, whichButton ->
//                                dialog.dismiss()
//                                noteListAdapter.notifyItemChanged(position)
//                            }
//                            .show()
//                    }
//
//
//                }
//            }
//
//        }
//
//        val touchHelper = ItemTouchHelper(swipeGesture)
//        touchHelper.attachToRecyclerView(binding.recyclerviewNotesHome)
//
//    }


    private fun onDataLoaded() {
        skeleton.showOriginal()
//        swipe()
        drag()
    }

    override fun onItemClick(note: NotesModel) {
        // Here, you can open the ResultActivity and pass the selected note's data
        val intent = Intent(this@NotesHome, AddNotePage::class.java)
        intent.putExtra("from_item", true)
        intent.putExtra("note_id", note.Note_id)
        intent.putExtra("note_title", note.Note_title)
        intent.putExtra("note_content", note.Note_content)
//        intent.putExtra("note_image", note.Note_image)
//        intent.putExtra("note_color", note.Note_color)
        // Add more data as needed...
        startActivity(intent)
        finish()
    }

    fun searchviewClose(){

        binding.editSearchview.setText("")
        binding.constSearchview.visibility = View.GONE
        binding.constToolbarWithoutSearchview.visibility = View.VISIBLE
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.root.getWindowToken(), 0)

        if(noteList.isEmpty()){
            binding.swipeToRefresh.visibility = View.GONE
            binding.emptyLottieAnimation.visibility = View.VISIBLE
        }else {
            binding.swipeToRefresh.visibility = View.VISIBLE
            binding.emptyLottieAnimation.visibility = View.GONE
            loadData()
        }

    }


    override fun onBackPressed() {
        if(binding.constSearchview.isVisible){
            searchviewClose()
        }else{

            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                doubleBackToExitPressedOnce = false
            }, 2000)

        }

    }


}