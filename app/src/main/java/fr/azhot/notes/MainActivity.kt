package fr.azhot.notes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import fr.azhot.notes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    // variables
    private lateinit var binding: ActivityMainBinding


    // overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(setupViewBinding())
    }


    // functions
    private fun setupViewBinding(): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }
}