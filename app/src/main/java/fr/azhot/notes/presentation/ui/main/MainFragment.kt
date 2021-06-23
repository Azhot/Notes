package fr.azhot.notes.presentation.ui.main

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import fr.azhot.notes.R
import fr.azhot.notes.data.database.DummyNotesGenerator
import fr.azhot.notes.databinding.CellNoteBinding
import fr.azhot.notes.databinding.FragmentMainBinding
import fr.azhot.notes.util.TEXT_PREFIX
import fr.azhot.notes.util.TITLE_PREFIX

class MainFragment : Fragment(), NotesAdapter.OnClickListener {

    // variables
    private lateinit var binding: FragmentMainBinding
    private lateinit var adapter: NotesAdapter


    // overridden functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        return setupViewBinding(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setupNotesRecyclerView()
        setupExtendedFab()
        setupAddNoteFab()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete -> {
                adapter.deleteSelected()
                return true
            }
            R.id.send -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // callbacks
    override fun onClick(position: Int, binding: CellNoteBinding) {
        if (adapter.isInSelectionMode()) {
            adapter.switchSelectedState(position)
            return
        }
        val note = adapter.get(position)
        val directions = MainFragmentDirections.actionMainFragmentToCrudFragment(note.id)
        val extras = FragmentNavigatorExtras(
            binding.title to "$TITLE_PREFIX${note.id}",
            binding.text to "$TEXT_PREFIX${note.id}"
        )
        findNavController().navigate(directions, extras)
    }

    override fun onLongClick(position: Int) {
        adapter.switchSelectedState(position)
    }


    // functions
    private fun setupViewBinding(container: ViewGroup?): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun setupNotesRecyclerView() {
        adapter = NotesAdapter(DummyNotesGenerator.getNotes(), this)
        binding.notesRecyclerView.apply {
            adapter = this@MainFragment.adapter
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
            val directions = MainFragmentDirections.actionMainFragmentToCrudFragment("newNote")
            findNavController().navigate(directions)
        }
    }
}
