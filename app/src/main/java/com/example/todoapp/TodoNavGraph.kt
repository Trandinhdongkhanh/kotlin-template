package com.example.todoapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.ui.addedittask.AddEditTaskScreen
import com.example.todoapp.ui.taskdetail.TaskDetailsScreen
import com.example.todoapp.ui.tasks.TasksScreen

@Composable
fun TodoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = TodoRoutes.Tasks,
        modifier = modifier
    ) {
        composable<TodoRoutes.Tasks> {
            TasksScreen(
                onTaskClick = { navController.navigate(TodoRoutes.TaskDetails(it.id!!)) },
                onAddTask = { navController.navigate(TodoRoutes.AddEditTask()) }
            )
        }
        composable<TodoRoutes.AddEditTask> {
            AddEditTaskScreen(
                onTaskUpdated = {
                    navController.navigate(TodoRoutes.Tasks) {
                        popUpTo(TodoRoutes.Tasks) { inclusive = true }
                    }
                }
            )
        }
        composable<TodoRoutes.TaskDetails> {
            TaskDetailsScreen(
                onEditTask = { navController.navigate(TodoRoutes.AddEditTask(it.id)) }
            )
        }
    }
}