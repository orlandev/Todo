package com.orlandev.todo.data.source.local.dao

import androidx.room.*
import com.orlandev.todo.data.source.local.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Query("SELECT * FROM TodoEntity WHERE todoId=:todoId")
    suspend fun getTodoById(todoId: Int): TodoEntity?

    @Query("SELECT * FROM TodoEntity")
    fun getAllTodo(): Flow<List<TodoEntity>>

}