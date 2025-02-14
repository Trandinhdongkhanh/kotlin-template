package com.example.todoapp

import kotlinx.serialization.Serializable

object TodoRoutes {
    @Serializable
    object Tasks

    @Serializable
    data class AddEditTask(val taskId: Int? = null)

    @Serializable
    data class TaskDetails(val taskId: Int)
}