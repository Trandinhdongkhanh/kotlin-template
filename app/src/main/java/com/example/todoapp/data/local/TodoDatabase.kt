package com.example.todoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.local.dao.TaskDao
import com.example.todoapp.data.local.entity.LocalTask

@Database(entities = [LocalTask::class], exportSchema = false, version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}