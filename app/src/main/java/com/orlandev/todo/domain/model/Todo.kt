package com.orlandev.todo.domain.model

data class Todo(
    val todoId: Int,
    val title: String,
    val description: String,
    val isDone: Boolean,
    val startAt: Long,
    val endAt: Long,
)

/**
 * Return time in milliseconds of tasks duration to the end
 * and return -1 if the task is not end
 */
fun Todo.getTimeDuration(): Long {
    if (isDone)
        return endAt - startAt
    return -1
}