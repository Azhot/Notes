package fr.azhot.notes.repository

import androidx.lifecycle.LiveData
import fr.azhot.notes.data.database.dao.NoteDao
import fr.azhot.notes.domain.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotesSorted(): LiveData<List<Note>> {
        return noteDao.getAllNotesSorted()
    }

    suspend fun upsertNote(note: Note) {
        noteDao.upsertNote(note)
    }

    suspend fun updateNotes(vararg notes: Note) {
        noteDao.updateNotes(*notes)
    }

    suspend fun deleteNote(note: Note) {
        noteDao.deleteNote(note)
    }

    suspend fun deleteNotes(vararg notes: Note) {
        noteDao.deleteNotes(*notes)
    }
}