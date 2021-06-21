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
        fun onClickDeleteButton(position: Int)
    }


    // viewHolder
    class ViewHolder(
        private val binding: CellNoteBinding,
        private val listener: OnClickListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            listener.onClick(adapterPosition)
        }

        override fun onLongClick(v: View?): Boolean {
            listener.onLongClick(adapterPosition)
            return true
        }

        fun bind(note: Note) {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            binding.title.text = note.title
            binding.deleteButton.setOnClickListener {
                listener.onClickDeleteButton(adapterPosition)
            }
        }

        fun setSelected(selected: Boolean) {
            if (selected) {
                binding.deleteButton.visibility = View.VISIBLE
                binding.root.elevation = 12F
                itemView.isSelected = true
            } else {
                binding.deleteButton.visibility = View.GONE
                binding.root.elevation = 0F
                itemView.isSelected = false
            }
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

    fun remove(position: Int) {
        notes.removeAt(position)
        notifyItemRemoved(position)
    }


    // Selection functions
    fun isInSelectionMode(): Boolean {
        for (i in notes) {
            if (i.second) return true
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
        notes.forEachIndexed { index, pair ->
            if (pair.second) {
                notes[index] = notes[index].first to false
                notifyItemChanged(index)
            }
        }
    }
}