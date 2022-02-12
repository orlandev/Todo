package com.orlandev.todo.ui.screens.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.orlandev.todo.domain.model.Todo
import com.orlandev.todo.domain.repository.TodoRepository
import com.orlandev.todo.utils.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
    private val repository: TodoRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var todo by mutableStateOf<Todo?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var description by mutableStateOf("")
        private set

    private val _uiEvents = Channel<UiEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    init {
        val todoId = savedStateHandle.get<Int>("todoId")
        if (todoId != -1) {
            viewModelScope.launch {
                repository.getTodoById(todoId = todoId!!).let { currentTodo ->
                    title = currentTodo?.title ?: ""
                    description = currentTodo?.description ?: ""
                    this@AddEditTodoViewModel.todo = currentTodo
                }
            }
        }
    }


    fun onEvents(events: AddEditTodoEvents) {
        when (events) {
            is AddEditTodoEvents.OnTitleChanged -> {
                title = events.title
            }
            is AddEditTodoEvents.OnDescriptionChanged -> {
                description = events.description
            }
            is AddEditTodoEvents.OnSaveTodoClick -> {
                viewModelScope.launch {
                    if (title.isEmpty()) {
                        sendUiEvents(
                            UiEvents.ShowSnackBar(
                                message = "The title can't be empty."
                            )
                        )
                        return@launch
                    }
                    repository.insertTodo(
                        Todo(
                            title = title,
                            description = description,
                            isDone = todo?.isDone ?: false,
                            todoId = todo?.todoId ?: 0,
                            startAt = System.currentTimeMillis(),
                            endAt = System.currentTimeMillis() + 50000 //Only for test
                        )
                    )
                    sendUiEvents(UiEvents.PopBackStack)
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