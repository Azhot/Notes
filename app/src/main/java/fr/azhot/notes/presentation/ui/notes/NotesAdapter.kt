package fr.azhot.notes.presentation.ui.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import fr.azhot.notes.databinding.CellNoteBinding
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.util.Constants.ROOT_PREFIX
import fr.azhot.notes.util.Constants.TEXT_PREFIX
import fr.azhot.notes.util.Constants.TITLE_PREFIX
import java.util.*

class NotesAdapter(private val listener: NotesAdapterListener) :
    RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    // callbacks
    val itemTouchCallback = object : Callback() {
        override fun isLongPressDragEnabled() = false
        override fun isItemViewSwipeEnabled() = false

        override fun getMovementFlags(r: RecyclerView, v: RecyclerView.ViewHolder) =
            makeMovementFlags(UP or DOWN or START or END, 0)

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            val from = viewHolder.bindingAdapterPosition
            val to = target.bindingAdapterPosition
            swap(notes, from, to)
            notifyItemMoved(from, to)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            listener.onDragEnd(viewHolder as ViewHolder, notes)
            super.clearView(recyclerView, viewHolder)
        }

        fun swap(notes: MutableList<Note>, from: Int, to: Int) {
            if (from < to) {
                for (i in from until to) {
                    Collections.swap(notes, i, i + 1)
                    val temp = notes[i].position
                    notes[i].position = notes[i + 1].position
                    notes[i + 1].position = temp
                }
            } else {
                for (i in from downTo to + 1) {
                    Collections.swap(notes, i, i - 1)
                    val temp = notes[i].position
                    notes[i].position = notes[i - 1].position
                    notes[i - 1].position = temp
                }
            }
        }
    }


    // variables
    private var notes = mutableListOf<Note>()
    val currentNotes: List<Note> get() = notes
    private val selected = mutableListOf<Note>()
    val selectedNotes: List<Note> get() = selected

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
        holder.bind(notes[position])
    }

    override fun getItemCount() = notes.size


    // functions
    fun setNotes(notes: List<Note>) {
        when {
            this.notes.size == notes.size -> updateNotes(notes)
            this.notes.size < notes.size -> insertNotes(notes)
            this.notes.size > notes.size -> deleteNotes(notes)
        }

        for (i in selected.lastIndex downTo 0) {
            if (!this.notes.contains(selected[i])) selected.removeAt(i)
        }
    }

    private fun updateNotes(notes: List<Note>) {
        for (i in this.notes.indices) {
            if (this.notes[i] != notes[i]) {
                this.notes[i] = notes[i]
                notifyItemChanged(i)
            }
        }
    }

    private fun insertNotes(notes: List<Note>) {
        for (i in notes.indices) {
            if (!this.notes.contains(notes[i])) {
                this.notes.add(i, notes[i])
                notifyItemInserted(i)
            }
        }
    }

    private fun deleteNotes(notes: List<Note>) {
        for (i in this.notes.lastIndex downTo 0) {
            if (!notes.contains(this.notes[i])) {
                this.notes.removeAt(i)
                notifyItemRemoved(i)
            }
        }
    }

    fun isInSelectionMode() = selected.isNotEmpty()

    fun getSelectedNotesCount() = selected.count()

    fun clearSelectedState() {
        for (i in selected.lastIndex downTo 0) {
            val note = selected[i]
            selected.removeAt(i)
            notifyItemChanged(notes.indexOf(note))
        }
    }


    // listener
    interface NotesAdapterListener {
        fun onClick(viewHolder: ViewHolder, binding: CellNoteBinding)
        fun onLongClick(viewHolder: ViewHolder)
        fun onDragEnd(viewHolder: ViewHolder, notes: List<Note>)
    }


    // viewHolder
    inner class ViewHolder(
        private val binding: CellNoteBinding,
        private val listener: NotesAdapterListener
    ) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {

        override fun onClick(v: View?) {
            listener.onClick(this, binding)
        }

        override fun onLongClick(v: View?): Boolean {
            listener.onLongClick(this)
            return true
        }

        fun bind(note: Note) {
            setupSharedElementTransition(note.id)
            setupNoteTitle(note.title)
            setupNoteText(note.text)
            setElevation(note)
            itemView.isSelected = selected.contains(note)
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        fun setSelected() {
            val note = notes[bindingAdapterPosition]
            if (selected.contains(note)) {
                selected.remove(note)
                itemView.isSelected = false
            } else {
                selected.add(note)
                itemView.isSelected = true
            }
            setElevation(note)
        }

        private fun setupSharedElementTransition(noteId: Int) {
            ViewCompat.setTransitionName(binding.root, "$ROOT_PREFIX${noteId}")
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

        private fun setElevation(note: Note) {
            itemView.elevation = if (selected.contains(note)) 8F else 0F
        }
    }
}