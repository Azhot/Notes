package fr.azhot.notes.presentation.ui.main

import androidx.lifecycle.*
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.presentation.util.ViewState
import fr.azhot.notes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _viewState = MediatorLiveData<ViewState>()
    val viewState: LiveData<ViewState> get() = _viewState

    init {
        fetchNotes()
    }

    private fun fetchNotes() {
        _viewState.addSource(noteRepository.getAllNotesSorted()) { notes ->
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
        val result = async { noteRepository.insertNote(note) }
        if (result.await() == -1L) {
            noteRepository.updateNote(note)
            _viewState.postValue(ViewState.UpdateNoteState(note))
        } else {
            _viewState.postValue(ViewState.InsertNoteState(note))
        }
    }

    fun updateNotes(vararg notes: Note) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        noteRepository.updateNotes(*notes)
        _viewState.postValue(ViewState.UpdateNotesState(*notes))
    }

    fun deleteEmptyNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        noteRepository.deleteNote(note)
        _viewState.postValue(ViewState.EmptyNoteDeleteState(note))
    }

    fun deleteNotes(vararg notes: Note) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        noteRepository.deleteNotes(*notes)
        _viewState.postValue(ViewState.DeleteNotesState(*notes))
    }
}

class MainViewModelFactory(private val noteRepository: NoteRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(noteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}