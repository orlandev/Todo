package com.orlandev.todo.ui.screens.add_edit_todo

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.orlandev.todo.utils.UiEvents
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditTodoScreen(
    onPopBackStack: () -> Unit,
    addEditTodoViewModel: AddEditTodoViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(true) {
        addEditTodoViewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvents.PopBackStack -> onPopBackStack()
                is UiEvents.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actions
                    )
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        floatingActionButton = {
            FloatingActionButton(onClick = {

                addEditTodoViewModel.onEvents(
                    AddEditTodoEvents.OnSaveTodoClick
                )

            }) {
                Icon(Icons.Default.Check, contentDescription = "Save")
            }
        }

    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            TextField(
                value = addEditTodoViewModel.title,
                onValueChange = { addEditTodoViewModel.onEvents(AddEditTodoEvents.OnTitleChanged(it)) },
                placeholder = { Text(text = "Title") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                value = addEditTodoViewModel.description,
                onValueChange = {
                    addEditTodoViewModel.onEvents(
                        AddEditTodoEvents.OnDescriptionChanged(
                            it
                        )
                    )
                },
                placeholder = { Text(text = "Description") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5
            )

        }

    }

}