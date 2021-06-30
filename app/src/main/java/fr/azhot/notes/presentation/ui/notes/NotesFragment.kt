package fr.azhot.notes.presentation.ui.notes

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import fr.azhot.notes.R
import fr.azhot.notes.databinding.CellNoteBinding
import fr.azhot.notes.databinding.FragmentNotesBinding
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.presentation.ui.main.MainViewModel
import fr.azhot.notes.presentation.util.ViewState
import fr.azhot.notes.util.TEXT_PREFIX
import fr.azhot.notes.util.TITLE_PREFIX
import java.util.*

class NotesFragment : Fragment(), NotesAdapter.NotesAdapterListener {

    // variables
    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NotesAdapter
    private lateinit var itemTouchHelper: ItemTouchHelper
    private val viewModel: MainViewModel by activityViewModels()


    // overridden functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotesBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setupNotesRecyclerView()
        setupExtendedFab()
        setupAddNoteFab()
        setupViewStateObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                adapter.getSelectedNotesAndClear().let { selectedNotes ->
                    if (selectedNotes.isNotEmpty()) {
                        viewModel.deleteNotes(*selectedNotes)
                    }
                }
                return true
            }
            R.id.send -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(viewHolder: NotesAdapter.ViewHolder, binding: CellNoteBinding) {
        if (adapter.isInSelectionMode()) {
            viewHolder.setSelected()
            return
        }
        val note = adapter.currentNotes[viewHolder.bindingAdapterPosition]
        val directions = NotesFragmentDirections.actionMainFragmentToCrudFragment(note)
        val extras = FragmentNavigatorExtras(
            binding.title to "$TITLE_PREFIX${note.id}",
            binding.text to "$TEXT_PREFIX${note.id}"
        )
        findNavController().navigate(directions, extras)
    }

    override fun onLongClick(viewHolder: NotesAdapter.ViewHolder) {
        if (adapter.isInSelectionMode()) {
            viewHolder.setSelected()
            return
        }
        viewHolder.setSelected()
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onDragEnd(vararg notes: Note) {
        viewModel.updateNotes(*notes)
    }


    // functions
    private fun setupNotesRecyclerView() {
        adapter = NotesAdapter(this)
        itemTouchHelper = ItemTouchHelper(adapter.itemTouchCallback)
        binding.notesRecyclerView.apply {
            adapter = this@NotesFragment.adapter
            itemTouchHelper.attachToRecyclerView(this)
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }
    }

    private fun setupExtendedFab() {
        binding.extendedFab.apply {
            shrink()
            setOnClickListener {
                if (!binding.addNoteFab.isVisible) {
                    binding.addNoteFab.show()
                    binding.extendedFab.extend()
                } else {
                    binding.addNoteFab.hide()
                    binding.extendedFab.shrink()
                }
            }
        }
    }

    private fun setupAddNoteFab() {
        binding.addNoteFab.setOnClickListener {
            val directions = NotesFragmentDirections.actionMainFragmentToCrudFragment()
            findNavController().navigate(directions)
        }
    }

    private fun setupViewStateObserver() {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.FetchNotesState -> adapter.setNotes(viewState.data)
                is ViewState.InsertNoteState -> {
                    adapter.insertNote(viewState.note)
                    binding.notesRecyclerView.smoothScrollToPosition(0)
                }
                is ViewState.UpdateNoteState -> {
                    val position = adapter.updateNote(viewState.note)
                    binding.notesRecyclerView.smoothScrollToPosition(position)
                }
                else -> return@observe
            }
        }
    }
}
