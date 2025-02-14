package com.example.todoapp.ui.taskdetail

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.data.model.Task

@Composable
fun TaskDetailsScreen(
    modifier: Modifier = Modifier,
    viewmodel: TaskDetailViewmodel = hiltViewModel(),
    onEditTask: (Task) -> Unit
) {
    val uiState by viewmodel
    TaskDetailsScreen(
        modifier = modifier,
        onEditTask = onEditTask
    )
}

@Composable
private fun TaskDetailsScreen(
    modifier: Modifier = Modifier,
    onEditTask: (Task) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {},
        floatingActionButton = {
            SmallFloatingActionButton(onClick = { onEditTask() }) {
                Icon(Icons.Filled.Edit, contentDescription = null)
            }
        }
    ) { innerPadding ->
        TaskDetailsContent(
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun TaskDetailsContent(
    modifier: Modifier = Modifier
) {

}