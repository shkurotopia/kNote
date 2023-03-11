package xyz.shkurotopia.knote.viewmodel

import xyz.shkurotopia.knote.data.Note

data class NoteListState (
    val notes: List<Note> = emptyList()
)