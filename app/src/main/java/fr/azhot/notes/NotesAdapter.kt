package fr.azhot.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.azhot.notes.databinding.CellNoteBinding

class NotesAdapter(
    notes: List<Note>,
    private val listener: OnClickListener
) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private val notes: MutableList<Pair<Note, Boolean>> = notes.map { it to false }.toMutableList()


    // listener
    interface OnClickListener {
        fun onClick(position: Int)
        fun onLongClick(position: Int)
    }


    // viewHolder
    class ViewHolder(
        private val binding: CellNoteBinding,
        private val listener: OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            listener.onClick(bindingAdapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            listener.onLongClick(bindingAdapterPosition)
            return true
        }

        fun bind(note: Note) {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            binding.text.text = note.text
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

    fun delete(position: Int) {
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