package com.taskmanager.presentation.detail

import com.taskmanager.domain.model.Task

data class TaskDetailUiState(val isLoading: Boolean = true, val task: Task? = null, val error: String? = null, val deleted: Boolean = false)
