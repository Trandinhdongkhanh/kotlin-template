package com.example.todoapp.di

import android.content.Context
import androidx.room.Room
import com.example.todoapp.data.DefaultTaskRepository
import com.example.todoapp.data.TaskRepository
import com.example.todoapp.data.local.TodoDatabase
import com.example.todoapp.data.local.dao.TaskDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindTaskRepository(defaultTaskRepository: DefaultTaskRepository): TaskRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): TodoDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            TodoDatabase::class.java,
            "Tasks.db"
        ).build()

    @Provides
    fun provideTaskDao(todoDatabase: TodoDatabase): TaskDao = todoDatabase.taskDao()
}