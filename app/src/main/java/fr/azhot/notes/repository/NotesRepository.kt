package fr.azhot.notes.repository

import androidx.lifecycle.LiveData
import fr.azhot.notes.data.database.dao.NotesDao
import fr.azhot.notes.domain.model.Note

class NotesRepository(private val notesDao: NotesDao) {

    fun getAllNotesSorted(): LiveData<List<Note>> {
        return notesDao.getAllNotesSorted()
    }

    suspend fun updateNote(note: Note) {
        notesDao.updateNote(note)
    }

    suspend fun insertNote(note: Note): Long {
        return notesDao.insertNote(note)
    }

    suspend fun updateNotes(vararg notes: Note) {
        notesDao.updateNotes(*notes)
    }

    suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }

    suspend fun deleteNotes(vararg notes: Note) {
        notesDao.deleteNotes(*notes)
    }
}