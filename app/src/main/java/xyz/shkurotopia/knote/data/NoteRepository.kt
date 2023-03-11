package xyz.shkurotopia.knote.data

import kotlinx.coroutines.flow.Flow

class NoteRepository(
    private val noteDao: NoteDao
): INoteRepository {
    override fun getNotes(): Flow<List<Note>> {
       return noteDao.getNotes()
    }

    override suspend fun getNoteById(id: Int): Note? {
        return noteDao.getNoteById(id)
    }
    override suspend fun deleteNote(note: Note): Int {
        return noteDao.deleteNote(note)
    }

    override suspend fun insertNote(note: Note): Long {
        return noteDao.insertNote(note)
    }
}