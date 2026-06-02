package com.taskmanager.presentation.dashboard

data class DashboardUiState(val isLoading: Boolean = true, val total: Int = 0, val completed: Int = 0, val pending: Int = 0) { val progress: Int = if (total == 0) 0 else (completed * 100 / total) }
