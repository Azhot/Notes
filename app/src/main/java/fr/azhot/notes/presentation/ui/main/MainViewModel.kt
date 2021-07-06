package fr.azhot.notes.presentation.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.presentation.util.ViewState
import fr.azhot.notes.repository.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var notesRepository: NotesRepository
) : ViewModel() {

    private val _viewState = MediatorLiveData<ViewState>()
    val viewState: LiveData<ViewState> get() = _viewState
    val maxPosition get() = notesRepository.getMaxPosition()

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

    fun insertNotes(notes: List<Note>) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        notesRepository.insertNotes(notes)
        _viewState.postValue(ViewState.InsertNotesState(notes))
    }

    fun updateNotes(notes: List<Note>) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        notesRepository.updateNotes(notes)
        _viewState.postValue(ViewState.UpdateNotesState(notes))
    }

    fun removeEmptyNote() = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        _viewState.postValue(ViewState.RemoveEmptyNoteState)
        fetchNotes()
    }

    fun deleteNotes(notes: List<Note>) = viewModelScope.launch(Dispatchers.IO) {
        _viewState.postValue(ViewState.LoadingState)
        notesRepository.deleteNotes(notes)
        _viewState.postValue(ViewState.DeleteNotesState(notes))
    }
}