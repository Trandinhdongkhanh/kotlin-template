package com.example.todoapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class LocalTask(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    var title: String,
    var description: String,
    @ColumnInfo(name = "is_completed")
    var isCompleted: Boolean
)
