package xyz.shkurotopia.knote.viewmodel

data class EditorState(
    val text: String            = "",
    val hint: String            = "Enter Text...",
    val isHintVisible: Boolean  = true
)