package fr.azhot.notes.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

    @Query("SELECT * FROM note_table ORDER BY `position` DESC")
    fun getAllNotesSorted(): LiveData<List<NoteEntity>>

    @Query("SELECT IFNULL(MAX(position), 0) FROM note_table LIMIT 1")
    fun getMaxPosition(): LiveData<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: NoteEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNotes(vararg notes: NoteEntity): List<Long>

    @Update
    suspend fun updateNote(note: NoteEntity)

    @Update
    suspend fun updateNotes(vararg notes: NoteEntity)

    @Delete
    suspend fun deleteNote(note: NoteEntity)

    @Delete
    suspend fun deleteNotes(vararg notes: NoteEntity)
}