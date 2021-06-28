package fr.azhot.notes.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.azhot.notes.domain.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(vararg notes: Note): List<Long>

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Delete
    suspend fun deleteNotes(vararg notes: Note)

    @Transaction
    suspend fun upsertNote(note: Note) {
        if (insertNote(note) == -1L) updateNote(note)
    }
}