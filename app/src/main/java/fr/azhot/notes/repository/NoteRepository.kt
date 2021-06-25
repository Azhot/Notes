package fr.azhot.notes.repository

import androidx.lifecycle.LiveData
import fr.azhot.notes.data.database.dao.NoteDao
import fr.azhot.notes.domain.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    fun getAll(): LiveData<List<Note>> {
        return noteDao.getAll()
    }

    suspend fun insert(vararg notes: Note) {
        noteDao.insert(*notes)
    }

    suspend fun update(vararg notes: Note) {
        noteDao.update(*notes)
    }

    suspend fun delete(vararg notes: Note) {
        noteDao.delete(*notes)
    }
}