package fr.azhot.notes.presentation.ui.events

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import fr.azhot.notes.databinding.FragmentEventsBinding

@AndroidEntryPoint
class EventsFragment : Fragment() {

    // variables
    private var _binding: FragmentEventsBinding? = null
    private val binding get() = _binding!!


    // overridden functions
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEventsBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // code goes here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}