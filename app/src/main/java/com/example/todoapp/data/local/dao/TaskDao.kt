package com.example.todoapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.todoapp.data.local.entity.LocalTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("select * from task")
    fun getTasksStream(): Flow<List<LocalTask>>
    @Query("UPDATE task SET is_completed = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)
    @Upsert
    suspend fun upsert(localTask: LocalTask)

}