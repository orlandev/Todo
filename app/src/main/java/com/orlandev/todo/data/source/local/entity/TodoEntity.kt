package com.orlandev.todo.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.orlandev.todo.domain.model.Todo


@Entity
data class TodoEntity(
    val title: String,
    val description: String,
    val isDone: Boolean,
    val startAt: Long,
    val endAt: Long,
) {
    @PrimaryKey(autoGenerate = true)
    var todoId: Int = 0
}

fun TodoEntity.toTodo(): Todo {
    return Todo(
        todoId, title, description, isDone, startAt, endAt
    )
}

fun Todo.toTodoEntity(): TodoEntity {
    return if (this.todoId == 0)
        TodoEntity(
            title, description, isDone, startAt, endAt
        )
    else {
        val objTodoEntity = TodoEntity(
            title, description, isDone, startAt, endAt
        )
        objTodoEntity.todoId = this.todoId
        objTodoEntity
    }
}