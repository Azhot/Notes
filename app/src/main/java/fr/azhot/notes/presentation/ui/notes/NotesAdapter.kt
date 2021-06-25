package fr.azhot.notes.presentation.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.azhot.notes.databinding.CellNoteBinding
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.util.TEXT_PREFIX
import fr.azhot.notes.util.TITLE_PREFIX

class NotesAdapter(private val listener: OnClickListener) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    // variables
    private val differCallback = object : DiffUtil.ItemCallback<Note>() {
        override fun areItemsTheSame(oldItem: Note, newItem: Note) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Note, newItem: Note) =
            oldItem.toString() == newItem.toString()
    }
    private val differ = AsyncListDiffer(this, differCallback)
    private val selected = mutableListOf<Note>()


    // listener
    interface OnClickListener {
        fun onClick(position: Int, binding: CellNoteBinding)
        fun onLongClick(position: Int)
    }


    // viewHolder
    inner class ViewHolder(
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
            setupIfSelected(note)
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

        fun setupIfSelected(note: Note) {
            binding.root.elevation = if (selected.contains(note)) 16F else 0F
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
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    // functions
    fun setNotes(notes: List<Note>) {
        differ.submitList(notes)
    }

    @Throws(IndexOutOfBoundsException::class)
    fun get(position: Int): Note {
        return differ.currentList[position]
    }

    fun isInSelectionMode(): Boolean {
        return selected.isNotEmpty()
    }

    fun switchSelectedState(position: Int) {
        val note = differ.currentList[position]
        if (selected.contains(note)) {
            selected.remove(note)
        } else {
            selected.add(note)
        }
        notifyItemChanged(position)
    }

    fun clearSelectedState() {
        selected.clear()
    }

    fun getSelectedNotes(): Array<out Note> {
        return selected.toTypedArray().also { selected.clear() }
    }
}