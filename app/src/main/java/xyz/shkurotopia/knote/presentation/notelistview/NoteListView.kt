package xyz.shkurotopia.knote.presentation.notelistview

import android.annotation.SuppressLint
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import xyz.shkurotopia.knote.presentation.navigation.NavRoute
import xyz.shkurotopia.knote.viewmodel.NoteListEvent
import xyz.shkurotopia.knote.viewmodel.NoteListViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteListView(
    navController: NavController,
    viewModel: NoteListViewModel = hiltViewModel<NoteListViewModel>(),
) {
    val state = viewModel.noteListState.value
    rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    remember { MutableTransitionState(false) }
        .apply { targetState = true }
    val notesAvailable = state.notes.isNotEmpty()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Notes", color = MaterialTheme.colorScheme.primary) },
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            //TODO Fix FAB borders
            FloatingActionButton(
                onClick = { navController.navigate(NavRoute.EditorScreen.route) }
            ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note") }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            if (notesAvailable) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {

                    items(state.notes) { note ->
                        NoteItem(
                            note = note,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        NavRoute.EditorScreen.route + "?noteId=${note.id}&noteColor=${note.category}"
                                    )
                                },
                            onDeleteClick = {
                                viewModel.onEvent(NoteListEvent.DeleteNote(note))
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Note Deleted",
                                        actionLabel = "Undo"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.onEvent(NoteListEvent.RestoreNote)
                                    }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        if (state.notes.last() == note) {
                            Spacer(modifier = Modifier.height(60.dp))
                        }

                    }
                }
            } else {
                EmptyScreenText()
            }
        }

    }
}