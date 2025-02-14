package com.example.todoapp.ui.taskdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.example.todoapp.TodoRoutes
import com.example.todoapp.data.TaskRepository
import com.example.todoapp.data.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewmodel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val taskDetails = savedStateHandle.toRoute<TodoRoutes.TaskDetails>()


}

data class TaskDetailUiState(
    val task: Task? = null,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTaskDeleted: Boolean = false
)