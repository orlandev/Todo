package com.orlandev.todo.ui.screens.todo_list

import com.orlandev.todo.domain.model.Todo

sealed class TodoListEvent {
    data class DeleteTodo(val todo: Todo) : TodoListEvent()
    data class OnDoneChanged(val todo: Todo, val isDone: Boolean) : TodoListEvent()
    object OnUndoDeleteClick : TodoListEvent()
    data class OnTodoClick(val todo: Todo) : TodoListEvent()
    object OnAddTodoClick : TodoListEvent()
}