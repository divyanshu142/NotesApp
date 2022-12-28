package com.example.notesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.FragmentMainBinding
import com.example.notesapp.databinding.NoteitemBinding
import com.example.notesapp.modal.NoteResponce

class NoteAdapter(private val onClicknote: (NoteResponce) -> Unit) : androidx.recyclerview.widget.ListAdapter<NoteResponce, NoteAdapter.NoteViewHolder>(compereterdiffutile()){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteitemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        note?.let {
            holder.bind(it)
        }

    }

    inner class NoteViewHolder(private  val binding: NoteitemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(note: NoteResponce){
            binding.Title.text = note.title
            binding.Discription.text = note.description
            binding.root.setOnClickListener {
                onClicknote(note)
            }
        }
    }
}

class compereterdiffutile : DiffUtil.ItemCallback<NoteResponce>(){
    override fun areItemsTheSame(oldItem: NoteResponce, newItem: NoteResponce): Boolean {
        return  oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: NoteResponce, newItem: NoteResponce): Boolean {
           return oldItem == newItem
    }

}