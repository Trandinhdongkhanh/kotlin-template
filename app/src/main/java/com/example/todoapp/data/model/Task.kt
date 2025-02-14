package com.example.todoapp.data.model

data class Task(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    val isCompleted: Boolean = false
)
