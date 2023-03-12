package xyz.shkurotopia.knote.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import xyz.shkurotopia.knote.presentation.editorview.EditorView
import xyz.shkurotopia.knote.presentation.notelistview.NoteListView

@Composable
fun NoteNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoute.NOTE_LIST_SCREEN
    ) {
        composable(route = NavRoute.NOTE_LIST_SCREEN) {
           NoteListView(navController = navController)
        }

        composable(
            route = NavRoute.EDITOR_SCREEN + "?noteId={noteId}&noteCategory={noteCategory}",
            arguments = listOf(
                navArgument("noteId") {
                    type = NavType.IntType
                    defaultValue = -1
                },
                navArgument("noteCategory") {
                    type = NavType.IntType
                    defaultValue = 0
                }
            )
        ) {

            EditorView(navController = navController)
        }
    }
}