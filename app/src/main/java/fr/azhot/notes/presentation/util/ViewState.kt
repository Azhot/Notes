package fr.azhot.notes.presentation.util

import fr.azhot.notes.domain.model.Note

sealed class ViewState {
    object LoadingState : ViewState()
    class ErrorState(val message: String) : ViewState()
    class FetchNotesState(val data: List<Note>) : ViewState()
    class InsertNoteState(val note: Note) : ViewState()
    class UpdateNoteState(val note: Note) : ViewState()
    class UpdateNotesState(val notes: List<Note>) : ViewState()
    class EmptyNoteDeleteState(val note: Note) : ViewState()
    class DeleteNotesState(val notes: List<Note>) : ViewState()
}