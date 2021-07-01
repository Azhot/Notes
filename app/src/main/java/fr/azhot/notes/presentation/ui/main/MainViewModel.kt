package fr.azhot.notes.presentation.ui.main

import androidx.lifecycle.*
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.presentation.util.ViewState
import fr.azhot.notes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    private val _viewState = MediatorLiveData<ViewState>()
    val viewState: LiveData<ViewState> get() = _viewState

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        _viewState.addSource(notesRepository.getAllNotesSorted()) { notes ->
            _viewState.postValue(ViewState.LoadingState)
            try {
                _viewState.postValue(ViewState.FetchNotesState(notes))
            } catch (e: Exception) {
                _viewState.postValue(ViewState.ErrorState(e.message ?: "An error has occurred !"))
            }
        }
    }

    fun upsertNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        val result = async { notesRepository.insertNote(note) }
        if (result.await() == -1L) {
            notesRepository.updateNote(note)
            _viewState.postValue(ViewState.UpdateNoteState(note))
        } else {
            _viewState.postValue(ViewState.InsertNoteState(note))
        }
    }

    fun updateNotes(vararg notes: Note) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        notesRepository.updateNotes(*notes)
        _viewState.postValue(ViewState.UpdateNotesState(*notes))
    }

    fun deleteEmptyNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        notesRepository.deleteNote(note)
        _viewState.postValue(ViewState.EmptyNoteDeleteState(note))
    }

    fun deleteNotes(vararg notes: Note) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        notesRepository.deleteNotes(*notes)
        _viewState.postValue(ViewState.DeleteNotesState(*notes))
    }
}

class MainViewModelFactory(private val notesRepository: NotesRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(notesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}