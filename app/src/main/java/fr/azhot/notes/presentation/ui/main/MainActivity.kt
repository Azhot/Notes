package fr.azhot.notes.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.azhot.notes.application.NotesApplication
import fr.azhot.notes.data.util.Status
import fr.azhot.notes.databinding.ActivityMainBinding


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
        viewModel.notes.observe(this) { resource ->
            when (resource.status) {
                Status.LOADING -> binding.progressBar.visibility = View.VISIBLE
                Status.SUCCESS -> binding.progressBar.visibility = View.GONE
                Status.ERROR -> {
                    Snackbar.make(binding.root, resource.message!!, Snackbar.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}