package fr.azhot.notes.presentation.ui.notes

import android.os.Bundle
import android.transition.Fade
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import dagger.hilt.android.AndroidEntryPoint
import fr.azhot.notes.R
import fr.azhot.notes.databinding.CellNoteBinding
import fr.azhot.notes.databinding.FragmentNotesBinding
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.presentation.ui.main.MainViewModel
import fr.azhot.notes.presentation.util.ViewState
import fr.azhot.notes.util.Constants
import fr.azhot.notes.util.Constants.ROOT_PREFIX
import fr.azhot.notes.util.Constants.TEXT_PREFIX
import fr.azhot.notes.util.Constants.TITLE_PREFIX
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
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
        setupTransition()
        setupToolbar()
        setupNotesRecyclerView()
        setupViewStateObserver(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        binding.toolbar.visibility = View.GONE
        when (item.itemId) {
            R.id.delete -> return true.also { viewModel.deleteNotes(adapter.selectedNotes) } // todo : offer the user a step-back
            R.id.send -> return true // todo : implement
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(viewHolder: NotesAdapter.ViewHolder, binding: CellNoteBinding) {
        if (checkSelectionModeIsOn(viewHolder)) return
        val note = adapter.currentNotes[viewHolder.bindingAdapterPosition]
        navigateToCrudFragment(note, binding)
    }

    override fun onLongClick(viewHolder: NotesAdapter.ViewHolder) {
        if (checkSelectionModeIsOn(viewHolder)) return
        viewHolder.setSelected()
        itemTouchHelper.startDrag(viewHolder)
    }

    override fun onDragEnd(viewHolder: NotesAdapter.ViewHolder, notes: List<Note>) {
        viewModel.updateNotes(notes)
        binding.toolbar.visibility = View.VISIBLE
        binding.toolbar.title = adapter.getSelectedNotesCount().toString()
    }


    // functions
    private fun setupTransition() {
        postponeEnterTransition()
        enterTransition = Fade().apply { duration = Constants.SHORT_TRANSITION }
        exitTransition = Fade().apply { duration = Constants.SHORT_TRANSITION }
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationIcon(R.drawable.ic_close_24)
        binding.toolbar.setNavigationOnClickListener {
            adapter.clearSelectedState()
            binding.toolbar.visibility = View.GONE
        }
    }

    private fun setupNotesRecyclerView() {
        adapter = NotesAdapter(this)
        itemTouchHelper = ItemTouchHelper(adapter.itemTouchCallback)
        binding.notesRecyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(binding.notesRecyclerView)
    }

    private fun setupViewStateObserver(view: View) {
        viewModel.viewState.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.FetchNotesState -> {
                    adapter.setNotes(viewState.data)
                    (view.parent as? ViewGroup)?.doOnPreDraw {
                        startPostponedEnterTransition()
                    }
                }
                is ViewState.InsertNoteState -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(200)
                        binding.notesRecyclerView.smoothScrollToPosition(0)
                    }
                }
                is ViewState.InsertNotesState -> {
                    viewLifecycleOwner.lifecycleScope.launch {
                        delay(200)
                        binding.notesRecyclerView.smoothScrollToPosition(
                            adapter.currentNotes.indexOf(viewState.notes[0])
                        )
                    }
                }
                else -> return@observe
            }
        }
    }

    private fun checkSelectionModeIsOn(viewHolder: NotesAdapter.ViewHolder): Boolean {
        if (adapter.isInSelectionMode()) {
            viewHolder.setSelected()
            if (!adapter.isInSelectionMode()) {
                this.binding.toolbar.visibility = View.GONE
            }
            if (adapter.getSelectedNotesCount() > 0) {
                binding.toolbar.title = adapter.getSelectedNotesCount().toString()
            }
            return true
        }
        return false
    }

    private fun navigateToCrudFragment(note: Note, binding: CellNoteBinding) {
        val directions = NotesFragmentDirections.actionNotesFragmentToCrudFragment(note)
        val extras = FragmentNavigatorExtras(
            binding.root to "$ROOT_PREFIX${note.id}",
            binding.title to "$TITLE_PREFIX${note.id}",
            binding.text to "$TEXT_PREFIX${note.id}"
        )
        findNavController().navigate(directions, extras)
    }
}
