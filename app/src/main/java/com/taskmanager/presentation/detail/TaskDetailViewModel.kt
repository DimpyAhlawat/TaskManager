package com.taskmanager.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmanager.core.common.Result
import com.taskmanager.domain.usecase.DeleteTaskUseCase
import com.taskmanager.domain.usecase.GetTaskByIdUseCase
import com.taskmanager.domain.usecase.UpdateTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle, getTaskById: GetTaskByIdUseCase, private val updateTask: UpdateTaskUseCase, private val deleteTask: DeleteTaskUseCase) : ViewModel() {
    private val id: Long = checkNotNull(savedStateHandle["taskId"])
    private val _uiState = MutableStateFlow(TaskDetailUiState())
    val uiState: StateFlow<TaskDetailUiState> = _uiState.asStateFlow()
    init { viewModelScope.launch { getTaskById(id).collect { r -> _uiState.update { when(r){ is Result.Success -> it.copy(isLoading=false, task=r.data); is Result.Error -> it.copy(isLoading=false,error=r.message); Result.Loading -> it.copy(isLoading=true) } } } } }
    fun onEvent(event: TaskDetailUiEvent) { when(event){ TaskDetailUiEvent.ToggleCompleted -> viewModelScope.launch { _uiState.value.task?.let { updateTask(it.copy(isCompleted=!it.isCompleted)) } }; TaskDetailUiEvent.Delete -> viewModelScope.launch { deleteTask(id); _uiState.update{it.copy(deleted=true)} } } }
}
