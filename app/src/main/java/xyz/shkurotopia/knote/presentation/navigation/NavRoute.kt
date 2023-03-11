package xyz.shkurotopia.knote.presentation.navigation

sealed class NavRoute(val route: String) {
    object NoteListScreen   : NavRoute(NOTE_LIST_SCREEN)
    object EditorScreen     : NavRoute(EDITOR_SCREEN)

    companion object {
        const val NOTE_LIST_SCREEN  = "note_list_screen"
        const val EDITOR_SCREEN     = "editor_screen"
    }
}