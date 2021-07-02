package fr.azhot.notes.domain.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import fr.azhot.notes.data.NoteEntity
import fr.azhot.notes.domain.model.Note

object NoteMapper {
    private fun toNote(noteEntity: NoteEntity): Note =
        Note(
            id = noteEntity.id,
            title = noteEntity.title,
            text = noteEntity.text,
            position = noteEntity.position,
        )

    fun toNoteEntity(note: Note): NoteEntity =
        NoteEntity(
            id = note.id,
            title = note.title,
            text = note.text,
            position = note.position,
        )

    private fun toNotes(noteEntities: List<NoteEntity>): List<Note> =
        noteEntities.map { toNote(it) }

    fun toNoteEntities(notes: List<Note>): Array<out NoteEntity> =
        notes.map { toNoteEntity(it) }.toTypedArray()

    fun toLiveDataListNote(list: LiveData<List<NoteEntity>>): LiveData<List<Note>> =
        Transformations.map(list) { toNotes(it) }
}