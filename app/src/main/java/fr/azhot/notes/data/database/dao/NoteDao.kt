package fr.azhot.notes.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import fr.azhot.notes.domain.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table")
    fun getAll(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg notes: Note)

    @Update
    suspend fun update(vararg notes: Note)

    @Delete
    suspend fun delete(vararg notes: Note)
}