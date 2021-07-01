package fr.azhot.notes.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.azhot.notes.domain.model.Note

@Dao
interface NotesDao {

    @Query("SELECT * FROM note_table ORDER BY `position` DESC")
    fun getAllNotesSorted(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(vararg notes: Note): List<Long>

    @Update
    suspend fun updateNote(note: Note)

    @Update
    suspend fun updateNotes(vararg notes: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Delete
    suspend fun deleteNotes(vararg notes: Note)
}