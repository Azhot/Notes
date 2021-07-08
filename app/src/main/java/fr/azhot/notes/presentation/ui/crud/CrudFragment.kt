package fr.azhot.notes.presentation.ui.crud

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import fr.azhot.notes.databinding.FragmentCrudBinding
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.presentation.ui.main.MainViewModel
import fr.azhot.notes.util.Constants
import fr.azhot.notes.util.Constants.ROOT_PREFIX
import fr.azhot.notes.util.Constants.TEXT_PREFIX
import fr.azhot.notes.util.Constants.TITLE_PREFIX

@AndroidEntryPoint
class CrudFragment : Fragment() {

    // variables
    private var _binding: FragmentCrudBinding? = null
    private val binding get() = _binding!!
    private lateinit var note: Note
    private val args: CrudFragmentArgs by navArgs()
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
        postponeEnterTransition()
        args.note?.let {
            note = it
            setupSharedElementTransition()
            setupNoteContent()
        }
        setupMaxPositionObserver()
    }

    override fun onDestroyView() {
        when (binding.title.text.isEmpty() && binding.text.text.isEmpty()) {
            true -> viewModel.removeEmptyNote()
            false -> viewModel.upsertNote(
                note.copy(
                    title = binding.title.text.toString(),
                    text = binding.text.text.toString(),
                )
            )
        }
        super.onDestroyView()
        _binding = null
    }


    // functions
    private fun setupSharedElementTransition() {
        sharedElementEnterTransition =
            ChangeBounds().apply { duration = Constants.SHORT_TRANSITION }
        sharedElementReturnTransition =
            ChangeBounds().apply { duration = Constants.SHORT_TRANSITION }
        ViewCompat.setTransitionName(binding.root, "$ROOT_PREFIX${note.id}")
        ViewCompat.setTransitionName(binding.title, "$TITLE_PREFIX${note.id}")
        ViewCompat.setTransitionName(binding.text, "$TEXT_PREFIX${note.id}")
        startPostponedEnterTransition()
    }

    private fun setupNoteContent() {
        binding.title.setText(note.title)
        binding.text.setText(note.text)
    }

    private fun setupMaxPositionObserver() {
        viewModel.maxPosition.observe(viewLifecycleOwner) { maxPosition ->
            if (!this::note.isInitialized) note = Note(position = maxPosition + 1)
        }
    }
}