package fr.azhot.notes.presentation.ui.crud

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import fr.azhot.notes.databinding.FragmentCrudBinding
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.presentation.ui.main.MainViewModel
import fr.azhot.notes.util.Constants.ROOT_PREFIX
import fr.azhot.notes.util.Constants.SHORT_SHARED_ELEMENT_TRANSITION
import fr.azhot.notes.util.Constants.TEXT_PREFIX
import fr.azhot.notes.util.Constants.TITLE_PREFIX

class CrudFragment : Fragment() {

    // variables
    private var _binding: FragmentCrudBinding? = null
    private val binding get() = _binding!!
    private lateinit var note: Note
    private val viewModel: MainViewModel by activityViewModels()


    // overridden functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCrudBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeNote()
        setupSharedElementTransition()
        setupNoteTitle()
        setupNoteText()
    }

    override fun onDestroyView() {
        when (note.title.isEmpty() && note.text.isEmpty()) {
            true -> viewModel.deleteEmptyNote(note)
            false -> viewModel.upsertNote(note)
        }
        super.onDestroyView()
        _binding = null
    }


    // functions
    private fun initializeNote() {
        val args: CrudFragmentArgs by navArgs()
        note = args.note ?: Note(position = System.currentTimeMillis())
    }

    private fun setupSharedElementTransition() {
        sharedElementEnterTransition = TransitionInflater
            .from(this.context)
            .inflateTransition(android.R.transition.move)
            .also { it.duration = SHORT_SHARED_ELEMENT_TRANSITION }
        ViewCompat.setTransitionName(binding.root, "$ROOT_PREFIX${note.id}")
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