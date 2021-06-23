package fr.azhot.notes

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import fr.azhot.notes.databinding.FragmentCrudBinding

class CrudFragment : Fragment() {

    // variables
    private lateinit var binding: FragmentCrudBinding


    // overridden functions
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupSharedElementTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return setupViewBinding(container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNoteText()
    }


    // functions
    private fun setupViewBinding(container: ViewGroup?): View {
        binding = FragmentCrudBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun setupSharedElementTransition() {
        sharedElementEnterTransition =
            TransitionInflater.from(this.context).inflateTransition(android.R.transition.move)
                .apply {
                    duration = 200
                }
    }

    private fun setupNoteText() {
        val args: CrudFragmentArgs by navArgs()
        binding.text.text = args.note.text
        ViewCompat.setTransitionName(binding.text, args.note.id)
    }
}