package fr.azhot.notes.repository

import androidx.lifecycle.LiveData
import fr.azhot.notes.domain.model.Note

interface NotesRepository {

    fun getAllNotesSorted(): LiveData<List<Note>>

    suspend fun updateNote(note: Note)

    suspend fun insertNote(note: Note): Long

    suspend fun updateNotes(vararg notes: Note)

    suspend fun deleteNote(note: Note)

    suspend fun deleteNotes(vararg notes: Note)
}