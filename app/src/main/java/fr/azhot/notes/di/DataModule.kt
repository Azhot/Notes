package fr.azhot.notes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.azhot.notes.data.database.NotesDatabase
import fr.azhot.notes.data.database.NotesDao
import fr.azhot.notes.repository.NotesRepository
import fr.azhot.notes.repository.NotesRepositoryImpl
import fr.azhot.notes.util.Constants.NOTES_DATABASE_NAME
import javax.inject.Provider
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Singleton
    @Provides
    fun provideNotesDatabase(
        @ApplicationContext context: Context,
        notesDaoProvider: Provider<NotesDao>
    ): NotesDatabase = Room.databaseBuilder(context, NotesDatabase::class.java, NOTES_DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .addCallback(NotesDatabase.notesDatabaseCallback(notesDaoProvider))
        .build()

    @Singleton
    @Provides
    fun provideNotesDao(notesDatabase: NotesDatabase): NotesDao = notesDatabase.noteDao()

    @Singleton
    @Provides
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository = NotesRepositoryImpl(notesDao)
}