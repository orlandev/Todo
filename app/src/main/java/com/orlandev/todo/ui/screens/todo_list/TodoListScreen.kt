package com.orlandev.todo.ui.screens.todo_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.orlandev.todo.utils.UiEvents
import kotlinx.coroutines.flow.collect

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TodoListScreen(
    onNavigate: (UiEvents.Navigate) -> Unit,
    todoListViewModel: TodoListViewModel = hiltViewModel()
) {
    val todos = todoListViewModel.todos.collectAsState(initial = emptyList())

    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(true) {
        todoListViewModel.uiEvents.collect { event ->
            when (event) {
                is UiEvents.Navigate -> onNavigate(event)
                is UiEvents.ShowSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.actions
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        todoListViewModel.onEvent(
                            TodoListEvent.OnUndoDeleteClick
                        )
                    }
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                todoListViewModel.onEvent(TodoListEvent.OnAddTodoClick)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Todo")
            }
        }
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(todos.value) { currentTodo ->
                ListItem(
                    modifier = Modifier.clickable {
                        todoListViewModel.onEvent(TodoListEvent.OnTodoClick(currentTodo))
                    },
                    text = {
                        Text(text = currentTodo.title)
                    },
                    overlineText = {
                        Text(text = "${currentTodo.startAt - currentTodo.endAt}")
                    },
                    secondaryText = {
                        Text(text = currentTodo.description)
                    },
                    singleLineSecondaryText = true,
                    icon = {
                        Checkbox(checked = currentTodo.isDone, onCheckedChange = { })
                    },
                    trailing = {
                        IconButton(onClick = {
                            todoListViewModel.onEvent(TodoListEvent.DeleteTodo(currentTodo))
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete Todo")
                        }
                    }
                )
            }
        }

    }

}
        