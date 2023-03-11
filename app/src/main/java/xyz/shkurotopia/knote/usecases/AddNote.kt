package xyz.shkurotopia.knote.usecases

import android.util.Log
import xyz.shkurotopia.knote.data.Note
import xyz.shkurotopia.knote.data.NoteRepository
import kotlin.jvm.Throws

class AddNote(
    private val noteRepository: NoteRepository
) {

    @Throws(Note.InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw Note.InvalidNoteException("Note.title can't be empty")
        }
        if (note.content.isBlank()) {
            throw Note.InvalidNoteException("Note.content can't be empty")
        }

        val rowid = noteRepository.insertNote(note)
        Log.d(TAG, "Inserted note with rowid=$rowid")
    }

    companion object {
        private const val TAG = "AddNote Usecase"
    }
}