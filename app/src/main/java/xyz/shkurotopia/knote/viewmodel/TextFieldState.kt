package xyz.shkurotopia.knote.viewmodel

data class TextFieldState(
    val text: String            = "",
    val hint: String            = "Enter Text...",
    val isHintVisible: Boolean  = true
)