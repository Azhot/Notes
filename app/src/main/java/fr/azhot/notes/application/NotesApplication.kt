package fr.azhot.notes.application

import android.app.Application
import fr.azhot.notes.data.database.NotesDatabase
import fr.azhot.notes.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class NotesApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { NotesDatabase.getDatabase(this, applicationScope) }
    val noteRepository by lazy { NotesRepository(database.noteDao()) }
}