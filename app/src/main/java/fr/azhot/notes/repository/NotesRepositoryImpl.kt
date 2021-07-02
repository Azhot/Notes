package fr.azhot.notes.repository

import fr.azhot.notes.data.NotesDao
import fr.azhot.notes.domain.model.Note
import fr.azhot.notes.domain.util.NoteMapper

class NotesRepositoryImpl(private val notesDao: NotesDao) : NotesRepository {

    override fun getAllNotesSorted() = NoteMapper.toLiveDataListNote(notesDao.getAllNotesSorted())

    override fun getMaxPosition() = notesDao.getMaxPosition()

    override suspend fun insertNote(note: Note): Long {
        return notesDao.insertNote(NoteMapper.toNoteEntity(note))
    }

    override suspend fun updateNote(note: Note) {
        notesDao.updateNote(NoteMapper.toNoteEntity(note))
    }

    override suspend fun updateNotes(notes: List<Note>) {
        notesDao.updateNotes(*NoteMapper.toNoteEntities(notes))
    }

    override suspend fun deleteNote(note: Note) {
        notesDao.deleteNote(NoteMapper.toNoteEntity(note))
    }

    override suspend fun deleteNotes(notes: List<Note>) {
        notesDao.deleteNotes(*NoteMapper.toNoteEntities(notes))
    }
}