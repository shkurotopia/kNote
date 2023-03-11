package xyz.shkurotopia.knote.usecases

import android.util.Log
import xyz.shkurotopia.knote.data.INoteRepository
import xyz.shkurotopia.knote.data.Note

class GetNote(
    private val noteRepository: INoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        val note = noteRepository.getNoteById(id)
        Log.d(TAG, "Fetched row with rowid = $note")

        return note
    }

    companion object {
        private const val TAG = "GetNote Usecase"
    }
}