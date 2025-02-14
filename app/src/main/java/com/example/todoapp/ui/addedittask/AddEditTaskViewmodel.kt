package com.example.todoapp.ui.addedittask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.todoapp.TodoRoutes
import com.example.todoapp.data.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTaskViewmodel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val addEditTask = savedStateHandle.toRoute<TodoRoutes.AddEditTask>()
    private var _uiState: MutableStateFlow<AddEditTaskUiState> =
        MutableStateFlow(AddEditTaskUiState())
    val uiState: StateFlow<AddEditTaskUiState> = _uiState.asStateFlow()

    fun onTitleChanged(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun onDescriptionChanged(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun saveTask() {
        if (addEditTask.taskId == null) {
            createTask()
        } else {
            updateTask()
        }
    }

    private fun createTask() {
        viewModelScope.launch {
            taskRepository.createTask(
                title = uiState.value.title,
                description = uiState.value.description
            )
            _uiState.update { it.copy(isTaskSaved = true) }
        }
    }

    private fun updateTask() {
        viewModelScope.launch {
            taskRepository.updateTask(
                title = uiState.value.title,
                description = uiState.value.description,
                taskId = addEditTask.taskId!!
            )
            _uiState.update { it.copy(isTaskSaved = true) }
        }
    }
}

data class AddEditTaskUiState(
    val title: String = "",
    val description: String = "",
    val isTaskSaved: Boolean = false
)