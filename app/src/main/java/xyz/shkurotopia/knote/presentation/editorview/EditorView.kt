package xyz.shkurotopia.knote.presentation.editorview

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Note
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest
import xyz.shkurotopia.knote.data.Note
import xyz.shkurotopia.knote.viewmodel.EditorEvents
import xyz.shkurotopia.knote.viewmodel.EditorViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorView(
    navController: NavController,
    viewModel: EditorViewModel = hiltViewModel(),
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditorViewModel.UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(
                        message = event.msg
                    )
                }

                is EditorViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(EditorEvents.SaveNote)
                },
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save")
            }
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Note.noteCategories.forEach() { category ->
                    IconButton(
                        onClick = {
                            viewModel.onEvent(EditorEvents.ChangeCategory(category))
                        },
                        modifier = Modifier
                            .size(64.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .border(
                                width = 3.0.dp,
                                color = if (viewModel.noteCategory.value == category) {
                                    MaterialTheme.colorScheme.error
                                } else Color.Transparent,
                                shape = CircleShape
                            )
                    ) {
                        val ico: ImageVector
                        @Suppress("LiftReturnOrAssignment")
                        when(category) {
                            0 -> {
                                ico = Icons.Default.Check
                            }
                            1 -> {
                                ico = Icons.Default.Favorite
                            }
                            2 -> {
                                ico = Icons.Default.Note
                            }
                            else -> {
                                ico = Icons.Default.Error
                            }
                        }

                        Icon(imageVector = ico, contentDescription = "Category Select Button")
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextFiled(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(EditorEvents.ChangeTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(EditorEvents.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineSmall
            )
            Divider(thickness = 1.dp, color = MaterialTheme.colorScheme.tertiary)
            Spacer(modifier = Modifier.height(16.dp))

            TransparentHintTextFiled(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(EditorEvents.ChangeContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(EditorEvents.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}