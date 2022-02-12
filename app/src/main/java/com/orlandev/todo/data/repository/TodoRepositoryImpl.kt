package com.orlandev.todo.data.repository

import com.orlandev.todo.data.source.local.dao.TodoDao
import com.orlandev.todo.data.source.local.entity.toTodo
import com.orlandev.todo.data.source.local.entity.toTodoEntity
import com.orlandev.todo.domain.model.Todo
import com.orlandev.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val todoDao: TodoDao
) : TodoRepository {
    override suspend fun insertTodo(todo: Todo) {
        todoDao.insertTodo(todo.toTodoEntity())
    }

    override suspend fun deleteTodo(todo: Todo) {
        todoDao.deleteTodo(todo.toTodoEntity())
    }

    override suspend fun getTodoById(todoId: Int): Todo? {
        return todoDao.getTodoById(todoId = todoId)?.toTodo()
    }

    override fun getAllTodo(): Flow<List<Todo>> {
        return todoDao.getAllTodo().map { currentData -> currentData.map { it.toTodo() } }
    }
}