package xyz.shkurotopia.knote.viewmodel

import xyz.shkurotopia.knote.data.Note

sealed class NoteListEvent {
    data class DeleteNote(val note: Note) : NoteListEvent()
    object RestoreNote : NoteListEvent()
}