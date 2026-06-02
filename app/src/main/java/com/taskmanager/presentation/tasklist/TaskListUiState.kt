package com.taskmanager.presentation.tasklist

import com.taskmanager.domain.model.Task

data class TaskListUiState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val tasks: List<Task> = emptyList(),
    val query: String = "",
    val filter: TaskFilter = TaskFilter.ALL,
    val error: String? = null
) {
    val visibleTasks: List<Task> = tasks.filter { task ->
        val matchesQuery = task.title.contains(query, ignoreCase = true) || task.description.contains(query, ignoreCase = true)
        val matchesFilter = when (filter) {
            TaskFilter.ALL -> true
            TaskFilter.COMPLETED -> task.isCompleted
            TaskFilter.PENDING -> !task.isCompleted
        }
        matchesQuery && matchesFilter
    }
}
