package fr.azhot.notes

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import fr.azhot.notes.databinding.CellNoteBinding
import fr.azhot.notes.databinding.FragmentMainBinding

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
        val directions = MainFragmentDirections.actionMainFragmentToCrudFragment(note)
        val extras = FragmentNavigatorExtras(binding.text to note.id)
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
        adapter = NotesAdapter(DummyNotesGenerator.notes, this)
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
        binding.extendedFab.shrink()
        binding.extendedFab.setOnClickListener {
            if (!binding.addNoteFab.isVisible) {
                binding.addNoteFab.show()
                binding.extendedFab.extend()
            } else {
                binding.addNoteFab.hide()
                binding.extendedFab.shrink()
            }
        }
    }

    private fun setupAddNoteFab() {
        binding.addNoteFab.setOnClickListener {
            adapter.add(
                Note("Test ${adapter.itemCount + 1} with a long name to test Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam tincidunt est nec arcu rutrum, ac tincidunt ex consequat. Aenean suscipit cursus nulla, id placerat ")
            )
        }
    }
}
