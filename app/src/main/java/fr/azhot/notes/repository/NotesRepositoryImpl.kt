package fr.azhot.notes.repository

import androidx.lifecycle.LiveData
import fr.azhot.notes.data.database.NotesDao
import fr.azhot.notes.domain.model.Note

class NotesRepositoryImpl(private val notesDao: NotesDao) : NotesRepository {

    override fun getAllNotesSorted(): LiveData<List<Note>> {
        return notesDao.getAllNotesSorted()
    }

    override suspend fun updateNote(note: Note) {
        notesDao.updateNote(note)
    }

    override suspend fun insertNote(note: Note): Long {
        return notesDao.insertNote(note)
    }

    override suspend fun updateNotes(vararg notes: Note) {
        notesDao.updateNotes(*notes)
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(note)
    }

    override suspend fun deleteNotes(vararg notes: Note) {
        notesDao.deleteNotes(*notes)
    }
}