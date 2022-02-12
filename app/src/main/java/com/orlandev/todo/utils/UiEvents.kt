package com.orlandev.todo.utils

sealed class UiEvents {

    object PopBackStack : UiEvents()

    data class Navigate(val route: String) : UiEvents()

    data class ShowSnackBar(
        val message: String,
        val actions: String? = null
    ) : UiEvents()




}
