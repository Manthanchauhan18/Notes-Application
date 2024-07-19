package com.example.notesdemo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.notesdemo.R
import com.example.notesdemo.databinding.CardviewRecyclerviewNotesBinding
import com.example.notesdemo.model.NotesModel
import com.example.notesdemo.model.notes.Note
import com.example.notesdemo.model.notes.NoteItem

class NoteListAdapter(private val context: Context , private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<NoteListAdapter.ViewHolder>() {

    private var noteList: ArrayList<NotesModel> = ArrayList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: CardviewRecyclerviewNotesBinding? = null

        init {
            binding = CardviewRecyclerviewNotesBinding.bind(itemView)
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener.onItemClick(noteList[position])
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(note: NotesModel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_recyclerview_notes, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(noteList[position]) {

                val colorResId = when (position % 8) {
                    0 -> R.color.white
                    1 -> R.color.color1
                    2 -> R.color.color2
                    3 -> R.color.color3
                    4 -> R.color.color4
                    5 -> R.color.color5
                    6 -> R.color.color6
                    7 -> R.color.color7
                    // Set colors 3 to 6 similarly
                    else -> R.color.white
                }
                binding!!.cardviewRecyclerview.setCardBackgroundColor(
                    ContextCompat.getColor(
                        context,
                        colorResId
                    )
                )


                binding!!.cardviewTextTitle.setText(this.Note_title + ": " + this.Note_content)
//                binding!!.cardviewRecyclerview.setCardBackgroundColor(this.Note_color.toInt())

//                if(!this.Note_content.equals("") || !this.Note_image.equals("null")){
//                    if(!this.Note_content.equals("")){
////                        binding!!.cardviewTextContent.visibility = View.VISIBLE
//                    }
//                    if(!this.Note_image.equals("null")){
////                        binding!!.imageviewImageIcon.visibility = View.VISIBLE
//                    }
//                }


            }
        }


    }

    fun removeItem(position: Int) {
        if (noteList.isNotEmpty()) {
            noteList.removeAt(position)
            notifyDataSetChanged()
        }
    }

//    public fun undoItem(position: Int , ) {
//        if (noteList.isNotEmpty()) {
//            noteList.clear()
//            noteList.addAll(noteList)
//            notifyDataSetChanged()
//        }else{
//
//        }
//    }



    override fun getItemCount(): Int {
        return noteList.size
    }

    fun setList(list: ArrayList<NotesModel>){
        if(noteList.isNotEmpty()){
            noteList.clear()
        }
        noteList.addAll(list)
    }


}