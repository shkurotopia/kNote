package xyz.shkurotopia.knote.usecases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.shkurotopia.knote.data.Note
import xyz.shkurotopia.knote.data.NoteRepository

class GetNotes(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(note: Note): Flow<List<Note>> {
        val rows = noteRepository.getNotes().map { notes ->
            Log.d(TAG, "Fetched ${notes.size} rows")

            notes.sortedBy { it.timestamp }
        }

        return rows
    }

    companion object {
        private const val TAG = "GetNotes Usecase"
    }
}