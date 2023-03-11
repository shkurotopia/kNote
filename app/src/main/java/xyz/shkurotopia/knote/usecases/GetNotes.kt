package xyz.shkurotopia.knote.usecases

import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import xyz.shkurotopia.knote.data.INoteRepository
import xyz.shkurotopia.knote.data.Note

class GetNotes(
    private val noteRepository: INoteRepository
) {
    suspend operator fun invoke(): Flow<List<Note>> {
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