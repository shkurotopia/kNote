package xyz.shkurotopia.knote.data

import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    fun getNotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int): Note?

    suspend fun insertNote(note: Note): Long

    suspend fun deleteNote(note: Note): Int
}