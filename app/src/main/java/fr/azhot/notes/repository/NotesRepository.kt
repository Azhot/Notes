package fr.azhot.notes.repository

import androidx.lifecycle.LiveData
import fr.azhot.notes.domain.model.Note

interface NotesRepository {

    fun getAllNotesSorted(): LiveData<List<Note>>

    fun getMaxPosition(): LiveData<Int>

    suspend fun insertNote(note: Note): Long

    suspend fun updateNote(note: Note)

    suspend fun updateNotes(notes: List<Note>)

    suspend fun deleteNote(note: Note)

    suspend fun deleteNotes(notes: List<Note>)
}