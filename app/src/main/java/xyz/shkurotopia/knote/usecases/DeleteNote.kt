package xyz.shkurotopia.knote.usecases

import android.util.Log
import xyz.shkurotopia.knote.data.INoteRepository
import xyz.shkurotopia.knote.data.Note

class DeleteNote(
    private val noteRepository: INoteRepository
) {
    suspend operator fun invoke(note: Note) {
        val rows = noteRepository.deleteNote(note)
        Log.d(TAG, "Deleted $rows rows")
    }

    companion object {
        private const val TAG = "DeleteNote Usecase"
    }
}