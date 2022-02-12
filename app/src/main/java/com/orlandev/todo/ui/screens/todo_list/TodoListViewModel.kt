package com.orlandev.todo.ui.screens.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orlandev.todo.domain.model.Todo
import com.orlandev.todo.domain.repository.TodoRepository
import com.orlandev.todo.ui.navigation.Routes
import com.orlandev.todo.utils.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(private val todoRepository: TodoRepository) :
    ViewModel() {

    val todos = todoRepository.getAllTodo()

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    private var deletedTodo: Todo? = null

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.OnAddTodoClick -> {
                sendUiEvents(UiEvents.Navigate(Routes.ADD_EDIT_TODO))
            }
            is TodoListEvent.OnTodoClick -> {
                sendUiEvents(UiEvents.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.todoId}"))
            }
            is TodoListEvent.DeleteTodo -> {

                viewModelScope.launch {

                    deletedTodo = event.todo
                    todoRepository.deleteTodo(event.todo)
                    sendUiEvents(
                        UiEvents.ShowSnackBar(
                            message = "Todo deleted",
                            actions = "Undo"
                        )
                    )
                }

            }
            is TodoListEvent.OnDoneChanged -> {
                viewModelScope.launch {
                    todoRepository.insertTodo(
                        event.todo.copy(
                            isDone = event.todo.isDone
                        )
                    )
                }
            }
            is TodoListEvent.OnUndoDeleteClick -> {

                deletedTodo?.let { todo ->
                    viewModelScope.launch {
                        todoRepository.insertTodo(todo)
                    }
                }
            }
        }
    }

    private fun sendUiEvents(event: UiEvents) {
        viewModelScope.launch {
            _uiEvents.send(event)
        }
    }
}