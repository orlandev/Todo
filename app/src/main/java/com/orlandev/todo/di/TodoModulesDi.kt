package com.orlandev.todo.di

import android.content.Context
import com.orlandev.todo.data.repository.TodoRepositoryImpl
import com.orlandev.todo.data.source.local.TodoDatabase
import com.orlandev.todo.data.source.local.dao.TodoDao
import com.orlandev.todo.domain.repository.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModulesDi {

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext ctx: Context): TodoDatabase {
        return TodoDatabase.getInstance(ctx)
    }

    @Provides
    fun providesTodoDao(database: TodoDatabase): TodoDao {
        return database.todoDao
    }

    @Provides
    @Singleton
    fun provideTodoRepository(database: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(database.todoDao)
    }

}