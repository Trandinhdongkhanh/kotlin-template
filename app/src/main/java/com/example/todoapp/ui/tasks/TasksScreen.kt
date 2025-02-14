package com.example.todoapp.ui.tasks

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.R
import com.example.todoapp.data.model.Task
import com.example.todoapp.ui.theme.TodoAppTheme

@Composable
fun TasksScreen(
    modifier: Modifier = Modifier,
    viewModel: TasksViewmodel = hiltViewModel(),
    onTaskClick: (Task) -> Unit,
    onAddTask: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TasksScreen(
        modifier = modifier,
        uiState = uiState,
        onTaskCheckedChange = viewModel::completeTask,
        snackbarMessageShown = viewModel::snackBarMessageShown,
        onAddTask = onAddTask,
        onTaskClick = onTaskClick
    )
}

@Composable
private fun TasksScreen(
    modifier: Modifier = Modifier,
    uiState: TasksUiState,
    onTaskClick: (Task) -> Unit,
    onTaskCheckedChange: (Task, Boolean) -> Unit,
    onAddTask: () -> Unit,
    snackbarMessageShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier,
        topBar = {},
        floatingActionButton = {
            SmallFloatingActionButton(onClick = onAddTask) {
                Icon(Icons.Filled.Add, null)
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        when (uiState) {
            is TasksUiState.Error -> {}
            is TasksUiState.HasTasks -> {
                TasksContent(
                    modifier = Modifier.padding(innerPadding),
                    tasks = uiState.tasks,
                    onTaskClick = onTaskClick,
                    onTaskCheckedChange = onTaskCheckedChange,
                )
                uiState.userMessage?.let { message ->
                    val snackbarText = stringResource(message)
                    LaunchedEffect(snackbarHostState, message) {
                        snackbarHostState.showSnackbar(snackbarText)
                        snackbarMessageShown()
                    }
                }
            }

            TasksUiState.Loading -> {}
            TasksUiState.NoTasks -> {
                TasksEmptyContent(
                    modifier = Modifier.padding(innerPadding),
                    noTaskLabelRes = R.string.no_tasks_all,
                    noTaskIconRes = R.drawable.logo_no_fill
                )
            }
        }
    }
}

@Composable
private fun TasksEmptyContent(
    modifier: Modifier = Modifier,
    @DrawableRes noTaskIconRes: Int,
    @StringRes noTaskLabelRes: Int
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = noTaskIconRes),
            contentDescription = null,
            modifier = Modifier.size(96.dp)
        )
        Text(stringResource(id = noTaskLabelRes))
    }
}

@Composable
private fun TasksContent(
    modifier: Modifier = Modifier,
    tasks: List<Task>,
    onTaskClick: (Task) -> Unit,
    onTaskCheckedChange: (Task, Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.horizontal_margin))
    ) {
        Text(
            text = stringResource(R.string.label_all),
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.list_item_padding),
                vertical = dimensionResource(id = R.dimen.vertical_margin)
            ),
            style = MaterialTheme.typography.headlineSmall
        )
        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onTaskClick = onTaskClick,
                    onCheckedChange = { onTaskCheckedChange(task, it) }
                )
            }
        }
    }
}

@Composable
private fun TaskItem(
    task: Task,
    onCheckedChange: (Boolean) -> Unit,
    onTaskClick: (Task) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.horizontal_margin),
                vertical = dimensionResource(id = R.dimen.list_item_padding),
            )
            .clickable { onTaskClick(task) }
    ) {
        Checkbox(
            checked = task.isCompleted,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = task.title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(
                start = dimensionResource(id = R.dimen.horizontal_margin)
            ),
            textDecoration = if (task.isCompleted) {
                TextDecoration.LineThrough
            } else {
                null
            }
        )
    }
}

@Composable
@Preview
fun PreviewTasksEmpty() {
    TodoAppTheme {
        TasksScreen(
            uiState = TasksUiState.NoTasks,
            onTaskClick = {},
            onTaskCheckedChange = { _, _ -> },
            snackbarMessageShown = {},
            onAddTask = {}
        )
    }
}

@Composable
@Preview
fun PreviewTasks() {
    TodoAppTheme {
        TasksScreen(
            uiState = TasksUiState.HasTasks(
                userMessage = null,
                tasks = listOf(
                    Task(
                        title = "Title 1",
                        description = "Description 1",
                        isCompleted = false,
                        id = 1
                    ),
                    Task(
                        title = "Title 2",
                        description = "Description 2",
                        isCompleted = true,
                        id = 2
                    ),
                    Task(
                        title = "Title 3",
                        description = "Description 3",
                        isCompleted = true,
                        id = 3
                    ),
                    Task(
                        title = "Title 4",
                        description = "Description 4",
                        isCompleted = false,
                        id = 4
                    ),
                    Task(
                        title = "Title 5",
                        description = "Description 5",
                        isCompleted = true,
                        id = 5
                    ),
                ),
            ),
            onTaskClick = {},
            onTaskCheckedChange = { _, _ -> },
            snackbarMessageShown = {},
            onAddTask = {}
        )
    }
}