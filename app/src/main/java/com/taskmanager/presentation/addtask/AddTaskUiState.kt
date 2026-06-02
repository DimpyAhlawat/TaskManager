package com.taskmanager.presentation.addtask

import com.taskmanager.core.util.DateUtils
import com.taskmanager.domain.model.Priority

data class AddTaskUiState(val title: String = "", val description: String = "", val dueDate: String = DateUtils.format(DateUtils.todayEpochMillis()), val priority: Priority = Priority.MEDIUM, val isSaving: Boolean = false, val error: String? = null, val saved: Boolean = false)
