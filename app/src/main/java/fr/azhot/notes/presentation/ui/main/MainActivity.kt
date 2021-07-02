package fr.azhot.notes.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.azhot.notes.R
import fr.azhot.notes.databinding.ActivityMainBinding
import fr.azhot.notes.presentation.util.ViewState

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // variables
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()


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
                    ).show()
                }
                else -> {
                }
            }
        }
    }
}