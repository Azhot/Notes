package fr.azhot.notes.presentation.ui.main

import androidx.lifecycle.*
import fr.azhot.notes.data.util.Resource
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val noteRepository: NoteRepository) : ViewModel() {

    private val _notes = MediatorLiveData<Resource<List<Note>>>()
    val notes: LiveData<Resource<List<Note>>>
        get() = _notes

    init {
        setDataSource()
    }

    private fun setDataSource() {
        _notes.addSource(noteRepository.getAll()) { notes ->
            _notes.postValue(Resource.loading(null))
            try {
                _notes.postValue(Resource.success(notes))
            } catch (e: Exception) {
                _notes.postValue(
                    Resource.error(
                        data = null,
                        message = e.message ?: "An error has occurred !"
                    )
                )
            }
        }
    }

    fun insertNote(vararg notes: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.insert(*notes)
    }

    fun updateNote(vararg notes: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.update(*notes)
    }

    fun deleteNote(vararg notes: Note) = viewModelScope.launch(Dispatchers.IO) {
        noteRepository.delete(*notes)
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