package fr.azhot.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.azhot.notes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), NotesAdapter.OnClickListener {

    // variables
    private lateinit var binding: ActivityMainBinding


    // overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupNotesRecyclerView()
        setupExtendedFab()
        setupAddNoteFab()
    }


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

    override fun onClickDeleteButton(position: Int) {
        val adapter = (binding.notesRecyclerView.adapter as NotesAdapter)
        adapter.remove(position)
    }


    // functions
    private fun setupNotesRecyclerView() {
        binding.notesRecyclerView.apply {
            adapter = NotesAdapter(
                listOf(Note("Test1"), Note("Test2")),
                this@MainActivity
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