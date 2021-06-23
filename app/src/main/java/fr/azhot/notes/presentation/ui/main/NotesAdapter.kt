package fr.azhot.notes.presentation.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import fr.azhot.notes.databinding.CellNoteBinding
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.util.TEXT_PREFIX
import fr.azhot.notes.util.TITLE_PREFIX

class NotesAdapter(notes: List<Note>, private val listener: OnClickListener) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    // variables
    private val notes: MutableList<Pair<Note, Boolean>> = notes.map { it to false }.toMutableList()


    // listener
    interface OnClickListener {
        fun onClick(position: Int, binding: CellNoteBinding)
        fun onLongClick(position: Int)
    }


    // viewHolder
    class ViewHolder(
        private val binding: CellNoteBinding,
        private val listener: OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            listener.onClick(bindingAdapterPosition, binding)
        }

        override fun onLongClick(v: View?): Boolean {
            listener.onLongClick(bindingAdapterPosition)
            return true
        }

        fun bind(note: Note) {
            setupSharedElementTransition(note.id)
            setupNoteTitle(note.title)
            setupNoteText(note.text)
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        private fun setupSharedElementTransition(noteId: String) {
            ViewCompat.setTransitionName(binding.title, "$TITLE_PREFIX${noteId}")
            ViewCompat.setTransitionName(binding.text, "$TEXT_PREFIX${noteId}")
        }

        private fun setupNoteTitle(title: String) {
            binding.title.text = title
            binding.title.visibility = if (title.isEmpty()) View.GONE else View.VISIBLE

        }

        private fun setupNoteText(text: String) {
            binding.text.text = text
            binding.text.visibility = if (text.isEmpty()) View.GONE else View.VISIBLE
        }

        fun setSelected(selected: Boolean) {
            binding.root.elevation = if (selected) 16F else 0F
        }
    }


    // overridden functions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CellNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notes[position].first)
        holder.setSelected(notes[position].second)
    }

    override fun getItemCount(): Int {
        return notes.count()
    }


    // CRUD functions
    @Throws(IndexOutOfBoundsException::class)
    fun get(position: Int): Note {
        return notes[position].first
    }

    fun add(note: Note) {
        notes.add(note to false)
        notifyItemInserted(notes.lastIndex)
    }

    private fun delete(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }


    // Selection functions
    fun isInSelectionMode(): Boolean {
        for (pair in notes) {
            if (pair.second) return true
        }
        return false
    }

    fun switchSelectedState(position: Int) {
        notes[position] = if (notes[position].second) {
            notes[position].first to false
        } else {
            notes[position].first to true
        }
        notifyItemChanged(position)
    }

    fun clearSelectedState() {
        for (i in notes.indices) {
            if (notes[i].second) {
                notes[i] = notes[i].first to false
                notifyItemChanged(i)
            }
        }
    }

    fun deleteSelected() {
        for (i in notes.lastIndex downTo 0) {
            if (notes[i].second) {
                delete(i)
            }
        }
    }
}