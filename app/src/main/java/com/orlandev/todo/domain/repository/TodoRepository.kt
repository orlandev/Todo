package com.orlandev.todo.domain.repository

import com.orlandev.todo.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {

    suspend fun insertTodo(todo: Todo)

    suspend fun deleteTodo(todo: Todo)

    suspend fun getTodoById(todoId: Int): Todo?

    fun getAllTodo(): Flow<List<Todo>>

}