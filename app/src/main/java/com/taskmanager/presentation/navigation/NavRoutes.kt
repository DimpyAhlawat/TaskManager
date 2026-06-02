package com.taskmanager.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavRoutes(val route: String) {
    data object Dashboard : NavRoutes("dashboard")
    data object TaskList : NavRoutes("tasks")
    data object AddTask : NavRoutes("tasks/add")
    data object EditTask : NavRoutes("tasks/{taskId}/edit") { fun create(taskId: Long) = "tasks/$taskId/edit"; val arguments = listOf(navArgument("taskId") { type = NavType.LongType }) }
    data object TaskDetail : NavRoutes("tasks/{taskId}") { fun create(taskId: Long) = "tasks/$taskId"; val arguments = listOf(navArgument("taskId") { type = NavType.LongType }) }
}
