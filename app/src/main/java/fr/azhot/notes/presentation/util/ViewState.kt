package fr.azhot.notes.presentation.util

import fr.azhot.notes.domain.model.Note

sealed class ViewState {
    object LoadingState : ViewState()
    class ErrorState(val message: String) : ViewState()
    class RefreshNotesState(val data: List<Note>) : ViewState()
    class UpsertNoteState(val note: Note) : ViewState()
    class EmptyNoteDeleteState(val note: Note) : ViewState()
    class DeleteNotesState(vararg val notes: Note) : ViewState()
}