package com.example.todoapp.data

import com.example.todoapp.data.local.entity.LocalTask
import com.example.todoapp.data.model.Task

fun LocalTask.toExternal() = Task(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun Task.toLocal() = LocalTask(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)