package xyz.shkurotopia.knote.viewmodel

import androidx.compose.ui.focus.FocusState

sealed class EditorEvents {
    data class ChangeCategory(val category: Int) : EditorEvents()
    data class ChangeTitle(val title: String) : EditorEvents()
    data class ChangeContent(val content: String) : EditorEvents()
    data class ChangeTitleFocus(val focusState: FocusState) : EditorEvents()
    data class ChangeContentFocus(val focusState: FocusState) : EditorEvents()
    object SaveNote : EditorEvents()
}