package fr.azhot.notes.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import fr.azhot.notes.R
import fr.azhot.notes.application.NotesApplication
import fr.azhot.notes.databinding.ActivityMainBinding
import fr.azhot.notes.presentation.ui.notes.NotesFragmentDirections
import fr.azhot.notes.presentation.util.ViewState


class MainActivity : AppCompatActivity() {

    // variables
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(
            (application as NotesApplication).noteRepository
        )
    }


    // overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setupViewBinding())
        setupNotesObserver()
    }


    // functions
    private fun setupViewBinding(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun setupNotesObserver() {
        viewModel.viewState.observe(this) { viewState ->

            when (viewState is ViewState.LoadingState) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }

            when (viewState) {
                is ViewState.ErrorState -> Snackbar.make(
                    binding.root,
                    viewState.message,
                    Snackbar.LENGTH_SHORT
                ).show()
                is ViewState.EmptyNoteDeleteState -> {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.empty_note_deleted),
                        Snackbar.LENGTH_SHORT
                    )
                        .setAction(getString(R.string.cancel)) {
                            val directions =
                                NotesFragmentDirections.actionMainFragmentToCrudFragment(viewState.note)
                            findNavController(binding.navHostFragment.id).navigate(directions)
                        }
                        .show()
                }
                else -> {
                }
            }
        }
    }
}