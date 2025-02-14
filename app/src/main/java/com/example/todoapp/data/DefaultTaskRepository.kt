package com.example.todoapp.data

import com.example.todoapp.data.local.dao.TaskDao
import com.example.todoapp.data.local.entity.LocalTask
import com.example.todoapp.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultTaskRepository @Inject constructor(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getTasksStream(): Flow<List<Task>> {
        return taskDao.getTasksStream().map { localTasks ->
            localTasks.map { it.toExternal() }
        }
    }

    override suspend fun completeTask(taskId: Int) {
        taskDao.updateCompleted(taskId, true)
    }

    override suspend fun activateTask(taskId: Int) {
        taskDao.updateCompleted(taskId, false)
    }

    override suspend fun createTask(title: String, description: String) {
        val localTask = LocalTask(
            id = null,
            title = title,
            description = description,
            isCompleted = false
        )
        taskDao.upsert(localTask)
    }

    override suspend fun updateTask(title: String, description: String, taskId: Int) {
        TODO("Not yet implemented")
    }
}