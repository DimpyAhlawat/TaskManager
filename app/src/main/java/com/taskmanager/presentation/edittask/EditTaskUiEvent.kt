package com.taskmanager.presentation.edittask

import com.taskmanager.domain.model.Priority

sealed interface EditTaskUiEvent { data class TitleChanged(val value: String): EditTaskUiEvent; data class DescriptionChanged(val value: String): EditTaskUiEvent; data class DueDateChanged(val value: String): EditTaskUiEvent; data class PriorityChanged(val value: Priority): EditTaskUiEvent; data object Save: EditTaskUiEvent }
