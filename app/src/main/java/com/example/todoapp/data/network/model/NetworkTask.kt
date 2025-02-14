package com.example.todoapp.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NetworkTask(
    val id: Int? = null,
    val title: String = "",
    val description: String = "",
    @SerialName("is_completed")
    val isCompleted: Boolean = false
)