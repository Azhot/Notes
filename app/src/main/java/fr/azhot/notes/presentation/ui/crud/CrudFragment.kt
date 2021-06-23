package fr.azhot.notes.presentation.ui.crud

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.azhot.notes.data.database.DummyNotesGenerator
import fr.azhot.notes.databinding.FragmentCrudBinding
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.util.NEW_NOTE_TAG
import fr.azhot.notes.util.SHORT_SHARED_ELEMENT_TRANSITION
import fr.azhot.notes.util.TEXT_PREFIX
import fr.azhot.notes.util.TITLE_PREFIX

class CrudFragment : Fragment() {

    // variables
    private lateinit var binding: FragmentCrudBinding
    private lateinit var note: Note


    // overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeNote()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setupViewBinding(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSharedElementTransition()
        setupNoteTitle()
        setupNoteText()
    }

    override fun onDestroy() {
        if (note.title.isEmpty() && note.text.isEmpty()) {
            if (note.id != NEW_NOTE_TAG) {
                DummyNotesGenerator.deleteNote(note)
            }
            Toast.makeText(context, "Empty note was deleted", Toast.LENGTH_SHORT).show()
        } else {
            if (note.id == NEW_NOTE_TAG) {
                DummyNotesGenerator.addNote(Note(note.title, note.text))
            }
        }
        super.onDestroy()
    }


    // functions
    private fun initializeNote() {
        val args: CrudFragmentArgs by navArgs()
        if (args.noteId == NEW_NOTE_TAG) {
            note = Note(id = args.noteId)
        } else {
            try {
                note = DummyNotesGenerator.getNote(args.noteId)
            } catch (e: NoSuchElementException) {
                note = Note()
                findNavController().navigateUp()
                Log.wtf(this::class.simpleName, "Could not fetch note data from Database")
            }
        }
    }

    private fun setupViewBinding(container: ViewGroup?): View {
        binding = FragmentCrudBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun setupSharedElementTransition() {
        sharedElementEnterTransition = TransitionInflater
            .from(this.context)
            .inflateTransition(android.R.transition.move)
            .apply {
                duration = SHORT_SHARED_ELEMENT_TRANSITION
            }
        ViewCompat.setTransitionName(binding.title, "$TITLE_PREFIX${note.id}")
        ViewCompat.setTransitionName(binding.text, "$TEXT_PREFIX${note.id}")
    }

    private fun setupNoteTitle() {
        binding.title.apply {
            setText(note.title)
            doAfterTextChanged {
                note.title = (it ?: "").toString()
                DummyNotesGenerator.updateNote(note)
            }
        }
    }

    private fun setupNoteText() {
        binding.text.apply {
            setText(note.text)
            doAfterTextChanged {
                note.text = (it ?: "").toString()
                DummyNotesGenerator.updateNote(note)
            }
        }
    }
}