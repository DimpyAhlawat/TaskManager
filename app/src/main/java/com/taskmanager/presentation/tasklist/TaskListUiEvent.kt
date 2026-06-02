package com.taskmanager.presentation.tasklist

sealed interface TaskListUiEvent {
    data class SearchChanged(val query: String) : TaskListUiEvent
    data class FilterChanged(val filter: TaskFilter) : TaskListUiEvent
    data object Refresh : TaskListUiEvent
}
