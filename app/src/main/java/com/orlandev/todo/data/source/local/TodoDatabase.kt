package com.orlandev.todo.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.orlandev.todo.data.source.local.dao.TodoDao
import com.orlandev.todo.data.source.local.entity.TodoEntity

@Database(
    entities = [TodoEntity::class],
    version = 1
)
abstract class TodoDatabase() : RoomDatabase() {

    abstract val todoDao: TodoDao

    companion object {

        private const val DATABASE_NAME = "todo-db"

        @Volatile
        private var INSTANCE: TodoDatabase? = null
        fun getInstance(context: Context): TodoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}