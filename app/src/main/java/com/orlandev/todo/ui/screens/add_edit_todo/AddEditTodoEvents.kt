package com.orlandev.todo.ui.screens.add_edit_todo

sealed class AddEditTodoEvents {

    data class OnTitleChanged(val title: String) : AddEditTodoEvents()
    data class OnDescriptionChanged(val description: String) : AddEditTodoEvents()
    object OnSaveTodoClick : AddEditTodoEvents()

}