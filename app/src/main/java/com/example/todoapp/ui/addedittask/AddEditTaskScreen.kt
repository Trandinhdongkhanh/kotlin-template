package com.example.todoapp.ui.addedittask

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todoapp.R

@Composable
fun AddEditTaskScreen(
    modifier: Modifier = Modifier,
    viewmodel: AddEditTaskViewmodel = hiltViewModel(),
    onTaskUpdated: () -> Unit
) {
    val uiState by viewmodel.uiState.collectAsStateWithLifecycle()
    AddEditTaskScreen(
        modifier = modifier,
        onTitleChanged = viewmodel::onTitleChanged,
        onDescriptionChanged = viewmodel::onDescriptionChanged,
        saveTask = viewmodel::saveTask,
        uiState = uiState,
        onTaskUpdated = onTaskUpdated
    )
}

@Composable
private fun AddEditTaskScreen(
    modifier: Modifier = Modifier,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    saveTask: () -> Unit,
    uiState: AddEditTaskUiState,
    onTaskUpdated: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {},
        floatingActionButton = {
            SmallFloatingActionButton(onClick = saveTask) {
                Icon(Icons.Filled.Done, contentDescription = null)
            }
        }
    ) { innerPadding ->
        AddEditTaskContent(
            modifier = Modifier.padding(innerPadding),
            title = uiState.title,
            description = uiState.description,
            onTitleChanged = onTitleChanged,
            onDescriptionChanged = onDescriptionChanged
        )
        LaunchedEffect(uiState.isTaskSaved) {
            if (uiState.isTaskSaved) {
                onTaskUpdated()
            }
        }
    }
}

@Composable
private fun AddEditTaskContent(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.horizontal_margin))
            .verticalScroll(rememberScrollState())
    ) {
        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSecondary
        )
        OutlinedTextField(
            value = title,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = onTitleChanged,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.title_hint),
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            textStyle = MaterialTheme.typography.headlineSmall
                .copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            colors = textFieldColors
        )
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChanged,
            placeholder = { Text(stringResource(id = R.string.description_hint)) },
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth(),
            colors = textFieldColors
        )
    }
}