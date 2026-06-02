package com.taskmanager.presentation.addtask

import com.taskmanager.domain.model.Priority

sealed interface AddTaskUiEvent { data class TitleChanged(val value: String): AddTaskUiEvent; data class DescriptionChanged(val value: String): AddTaskUiEvent; data class DueDateChanged(val value: String): AddTaskUiEvent; data class PriorityChanged(val value: Priority): AddTaskUiEvent; data object Save: AddTaskUiEvent }
