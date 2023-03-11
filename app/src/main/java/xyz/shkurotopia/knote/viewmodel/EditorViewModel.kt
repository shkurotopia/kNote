package xyz.shkurotopia.knote.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import xyz.shkurotopia.knote.data.Note
import xyz.shkurotopia.knote.usecases.UseCases
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val noteUseCases: UseCases,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        TextFieldState(hint = "Enter title...")
    )
    val noteTitle: State<TextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        TextFieldState(hint = "Enter note text...")
    )
    val noteContent: State<TextFieldState> = _noteContent

    private val _noteCategory = mutableStateOf<Int>(0)
    val noteCategory: State<Int> = _noteCategory

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            if (noteId != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(noteId)?.also { note ->
                        currentNoteId = note.id

                        _noteTitle.value = noteTitle.value.copy(
                            text = note.title,
                            isHintVisible = false
                        )

                        _noteContent.value = noteContent.value.copy(
                            text = note.content,
                            isHintVisible = false
                        )

                        _noteCategory.value = note.category
                    }
                }
            }
        }
    }

    fun onEvent(e: EditorEvents) {
        when(e) {
            is EditorEvents.ChangeCategory -> {
                _noteCategory.value = e.category
            }
            is EditorEvents.ChangeContent -> {
                _noteContent.value = noteContent.value.copy(
                    text = e.content
                )
            }
            is EditorEvents.ChangeContentFocus -> {
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = _noteContent.value.text.isBlank() && !e.focusState.isFocused
                )
            }
            is EditorEvents.ChangeTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = e.title
                )
            }
            is EditorEvents.ChangeTitleFocus ->  {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = _noteTitle.value.text.isBlank() && !e.focusState.isFocused
                )
            }
            EditorEvents.SaveNote -> {
                viewModelScope.launch {
                    val note = Note(
                        id          =  currentNoteId,
                        title       = _noteTitle.value.text,
                        content     = _noteContent.value.text,
                        category    = _noteCategory.value,
                        timestamp   = System.currentTimeMillis()
                    )

                    try {
                        noteUseCases.addNote(note)
                        _eventFlow.emit(UiEvent.SaveNote)
                        Log.d(TAG, "Note Successfully aded")
                    } catch (ex: Note.InvalidNoteException) {
                        _eventFlow.emit(UiEvent.ShowSnackBar(
                            ex.message ?: "Couldn't save note"
                        ))
                        Log.e(TAG, ex.message ?: "Couldn't save note")
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackBar(val msg: String) : UiEvent()
        object SaveNote : UiEvent()
    }

    companion object {
        private const val TAG = "EditorViewModel"
    }
}