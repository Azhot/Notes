package fr.azhot.notes.application

import android.app.Application
import fr.azhot.notes.data.database.NoteDatabase
import fr.azhot.notes.repository.NoteRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { NoteDatabase.getDatabase(this, applicationScope) }
    val noteRepository by lazy { NoteRepository(database.noteDao()) }
}