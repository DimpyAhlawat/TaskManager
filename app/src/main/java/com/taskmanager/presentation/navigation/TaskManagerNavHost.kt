package com.taskmanager.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.taskmanager.presentation.addtask.AddTaskRoute
import com.taskmanager.presentation.dashboard.DashboardRoute
import com.taskmanager.presentation.detail.TaskDetailRoute
import com.taskmanager.presentation.edittask.EditTaskRoute
import com.taskmanager.presentation.tasklist.TaskListRoute

@Composable
fun TaskManagerNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavRoutes.Dashboard.route) {
        composable(NavRoutes.Dashboard.route) { DashboardRoute(onTasksClick = { navController.navigate(NavRoutes.TaskList.route) }) }
        composable(NavRoutes.TaskList.route) { TaskListRoute(onAddClick = { navController.navigate(NavRoutes.AddTask.route) }, onTaskClick = { navController.navigate(NavRoutes.TaskDetail.create(it)) }) }
        composable(NavRoutes.AddTask.route) { AddTaskRoute(onSaved = { navController.popBackStack() }) }
        composable(NavRoutes.TaskDetail.route, arguments = NavRoutes.TaskDetail.arguments) { TaskDetailRoute(onDeleted = { navController.popBackStack() }, onEdit = { navController.navigate(NavRoutes.EditTask.create(it)) }) }
        composable(NavRoutes.EditTask.route, arguments = NavRoutes.EditTask.arguments) { EditTaskRoute(onSaved = { navController.popBackStack() }) }
    }
}
