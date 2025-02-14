package com.example.todoapp.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.R
import com.example.todoapp.data.TaskRepository
import com.example.todoapp.data.model.Task
import com.example.todoapp.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewmodel @Inject constructor(
    private val taskRepository: TaskRepository,
) : ViewModel() {
    private val _taskStream = taskRepository.getTasksStream()
        .map { Result.Success(it) }
        .catch<Result<List<Task>>> { emit(Result.Error(it.message)) }
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

    val uiState: StateFlow<TasksUiState> = combine(
        _taskStream, _userMessage
    ) { taskStream, userMessage ->
        produceUiState(taskStream, userMessage)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
            initialValue = TasksUiState.Loading
        )

    companion object {
        private const val STOP_TIMEOUT_MILLIS: Long = 5000
    }

    private fun produceUiState(result: Result<List<Task>>, userMessage: Int?): TasksUiState {
        return when (result) {
            is Result.Success -> {
                if (result.data.isEmpty()) TasksUiState.NoTasks
                else TasksUiState.HasTasks(result.data, userMessage = userMessage)
            }
            is Result.Error -> {
                TasksUiState.Error(result.errorMessage)
            }
            is Result.Loading -> {
                TasksUiState.Loading
            }
        }
    }

    fun completeTask(task: Task, completed: Boolean) = viewModelScope.launch {
        if (completed) {
            taskRepository.completeTask(task.id!!)
            showSnackbarMessage(R.string.task_marked_complete)
        } else {
            taskRepository.activateTask(task.id!!)
            showSnackbarMessage(R.string.task_marked_active)
        }
    }

    private fun showSnackbarMessage(message: Int) {
        _userMessage.value = message
    }
    fun snackBarMessageShown() {
        _userMessage.value = null
    }
}

sealed interface TasksUiState {
    data object Loading : TasksUiState
    data class HasTasks(val tasks: List<Task>, val userMessage: Int?) : TasksUiState
    data object NoTasks : TasksUiState
    data class Error(val userMessage: String?) : TasksUiState
}