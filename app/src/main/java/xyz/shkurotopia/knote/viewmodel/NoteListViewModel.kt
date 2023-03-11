package xyz.shkurotopia.knote.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import xyz.shkurotopia.knote.data.Note
import xyz.shkurotopia.knote.usecases.UseCases
import javax.inject.Inject

@HiltViewModel
class NoteListViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    private var _noteListState = mutableStateOf(NoteListState())
    val noteListState: State<NoteListState> = _noteListState

    var deletedNote: Note? = null

    private var job: Job? = null

    init {
        getNotes()
    }

    fun onEvent(e: NoteListEvent) {
        when(e) {
            is NoteListEvent.DeleteNote -> {
                viewModelScope.launch {
                    useCases.deleteNote(e.note)
                    deletedNote = e.note
                }
            }
            NoteListEvent.RestoreNote -> {
                deletedNote?.let {
                    viewModelScope.launch {
                        useCases.addNote(it)
                        deletedNote = null
                    }
                }
            }
        }

    }

    private fun getNotes() {
        job?.cancel()

        job = useCases.getNotes().onEach {notes ->
            _noteListState.value = _noteListState.value.copy(
                notes = notes
            )
        }.launchIn(viewModelScope)
    }
}