package com.taskmanager.presentation.detail

sealed interface TaskDetailUiEvent { data object ToggleCompleted: TaskDetailUiEvent; data object Delete: TaskDetailUiEvent }
