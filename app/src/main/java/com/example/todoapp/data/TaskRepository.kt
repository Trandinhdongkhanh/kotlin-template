package com.example.todoapp.data

import com.example.todoapp.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksStream(): Flow<List<Task>>
    suspend fun completeTask(taskId: Int)
    suspend fun activateTask(taskId: Int)
    suspend fun createTask(title: String, description: String)
    suspend fun updateTask(title: String, description: String, taskId: Int)
}