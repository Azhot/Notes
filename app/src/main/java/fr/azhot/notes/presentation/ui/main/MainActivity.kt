package fr.azhot.notes.presentation.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.azhot.notes.R
import fr.azhot.notes.databinding.ActivityMainBinding
import fr.azhot.notes.presentation.ui.crud.CrudFragment
import fr.azhot.notes.presentation.ui.notes.NotesFragment
import fr.azhot.notes.presentation.ui.notes.NotesFragmentDirections
import fr.azhot.notes.presentation.util.ViewState


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // variables
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment
    private val viewModel: MainViewModel by viewModels()


    // overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariables()
        setContentView(binding.root)
        setupBottomNavigation()
        listenToDestinationChanges()
        setupNotesObserver()
    }


    // functions
    private fun initVariables() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
    }

    private fun setupBottomNavigation() {
        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            navHostFragment.navController
        )
    }

    private fun listenToDestinationChanges() {
        navHostFragment.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when ((destination as FragmentNavigator.Destination).className) {
                NotesFragment::class.qualifiedName -> {
                    showBottomNavigation()
                    binding.fab.setOnClickListener { navigateToCrudFragment() }
                }
                CrudFragment::class.qualifiedName -> hideBottomNavigation()
            }
        }
    }

    private fun showBottomNavigation() {
        binding.bottomAppBar.performShow()
        binding.fab.show()
    }

    private fun hideBottomNavigation() {
        binding.bottomAppBar.performHide()
        binding.fab.hide()
    }

    private fun navigateToCrudFragment() {
        val directions = NotesFragmentDirections.actionNotesFragmentToCrudFragment()
        navHostFragment.navController.navigate(directions)
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
                is ViewState.EmptyNoteDeleteState -> Snackbar.make(
                    binding.root,
                    getString(R.string.empty_note_deleted),
                    Snackbar.LENGTH_SHORT
                ).apply { anchorView = binding.fab }.show()
                else -> {
                }
            }
        }
    }
}