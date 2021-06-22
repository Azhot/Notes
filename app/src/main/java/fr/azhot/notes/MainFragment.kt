package fr.azhot.notes

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import fr.azhot.notes.databinding.FragmentMainBinding

class MainFragment : Fragment(), NotesAdapter.OnClickListener {

    // variables
    private lateinit var binding: FragmentMainBinding
    private lateinit var navController: NavController


    // overridden functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController() // Navigation.findNavController(view)
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
                (binding.notesRecyclerView.adapter as NotesAdapter).deleteSelected()
                return true
            }
            R.id.send -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    // callbacks
    override fun onClick(position: Int) {
        val adapter = (binding.notesRecyclerView.adapter as NotesAdapter)
        if (adapter.isInSelectionMode()) {
            adapter.switchSelectedState(position)
        }
    }

    override fun onLongClick(position: Int) {
        val adapter = (binding.notesRecyclerView.adapter as NotesAdapter)
        adapter.switchSelectedState(position)
    }


    // functions
    private fun setupNotesRecyclerView() {
        binding.notesRecyclerView.apply {
            adapter = NotesAdapter(
                listOf(Note("Test1"), Note("Test2")),
                this@MainFragment
            )
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
            (binding.notesRecyclerView.adapter as NotesAdapter).apply {
                add(
                    Note("Test ${this.itemCount + 1} with a long name to test Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam tincidunt est nec arcu rutrum, ac tincidunt ex consequat. Aenean suscipit cursus nulla, id placerat ")
                )
            }
        }
    }
}