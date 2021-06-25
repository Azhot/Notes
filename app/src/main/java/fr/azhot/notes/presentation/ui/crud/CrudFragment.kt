package fr.azhot.notes.presentation.ui.crud

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import fr.azhot.notes.R
import fr.azhot.notes.databinding.FragmentCrudBinding
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.presentation.ui.main.MainViewModel
import fr.azhot.notes.util.NEW_NOTE_TAG
import fr.azhot.notes.util.SHORT_SHARED_ELEMENT_TRANSITION
import fr.azhot.notes.util.TEXT_PREFIX
import fr.azhot.notes.util.TITLE_PREFIX

class CrudFragment : Fragment() {

    // variables
    private lateinit var binding: FragmentCrudBinding
    private lateinit var note: Note
    private val viewModel: MainViewModel by activityViewModels()


    // overridden functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setupViewBinding(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeNote()
        setupSharedElementTransition()
        setupNoteTitle()
        setupNoteText()
    }

    override fun onDestroy() {
        if (note.title.isEmpty() && note.text.isEmpty()) {
            viewModel.deleteNote(note)
            Snackbar.make(
                binding.root,
                getString(R.string.empty_note_deleted),
                Snackbar.LENGTH_SHORT
            ).show()
        } else {
            if (note.id == NEW_NOTE_TAG) {
                viewModel.insertNote(Note(note.title, note.text))
            } else {
                viewModel.updateNote(note)
            }
        }
        super.onDestroy()
    }


    // functions
    private fun initializeNote() {
        try {
            val args: CrudFragmentArgs by navArgs()
            note = args.note ?: Note(id = NEW_NOTE_TAG)
        } catch (e: Exception) {
            note = Note()
            findNavController().navigateUp()
            Log.wtf(this::class.simpleName, "Could not pass note between fragments", e)
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
            .also { it.duration = SHORT_SHARED_ELEMENT_TRANSITION }
        ViewCompat.setTransitionName(binding.title, "$TITLE_PREFIX${note.id}")
        ViewCompat.setTransitionName(binding.text, "$TEXT_PREFIX${note.id}")
    }

    private fun setupNoteTitle() {
        binding.title.apply {
            setText(note.title)
            doAfterTextChanged { note.title = (it ?: "").toString() }
        }
    }

    private fun setupNoteText() {
        binding.text.apply {
            setText(note.text)
            doAfterTextChanged { note.text = (it ?: "").toString() }
        }
    }
}