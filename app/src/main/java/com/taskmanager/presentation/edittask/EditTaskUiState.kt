package com.taskmanager.presentation.edittask

import com.taskmanager.domain.model.Priority

data class EditTaskUiState(val id: Long = 0, val title: String = "", val description: String = "", val dueDate: String = "", val priority: Priority = Priority.MEDIUM, val completed: Boolean = false, val isLoading: Boolean = true, val isSaving: Boolean = false, val error: String? = null, val saved: Boolean = false)
