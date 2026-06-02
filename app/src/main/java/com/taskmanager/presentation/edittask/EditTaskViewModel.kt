package com.taskmanager.presentation.edittask

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmanager.core.common.Result
import com.taskmanager.core.util.DateUtils
import com.taskmanager.domain.model.Task
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
class EditTaskViewModel @Inject constructor(savedStateHandle: SavedStateHandle, getTaskById: GetTaskByIdUseCase, private val updateTask: UpdateTaskUseCase) : ViewModel() {
    private val id: Long = checkNotNull(savedStateHandle["taskId"])
    private val _uiState = MutableStateFlow(EditTaskUiState())
    val uiState: StateFlow<EditTaskUiState> = _uiState.asStateFlow()
    init { viewModelScope.launch { getTaskById(id).collect { r -> when(r){ is Result.Success -> r.data?.let { _uiState.value = EditTaskUiState(it.id, it.title, it.description, DateUtils.format(it.dueDateMillis), it.priority, it.isCompleted, false) }; is Result.Error -> _uiState.update{it.copy(isLoading=false,error=r.message)}; Result.Loading -> Unit } } } }
    fun onEvent(event: EditTaskUiEvent) { when(event){ is EditTaskUiEvent.TitleChanged -> _uiState.update{it.copy(title=event.value)}; is EditTaskUiEvent.DescriptionChanged -> _uiState.update{it.copy(description=event.value)}; is EditTaskUiEvent.DueDateChanged -> _uiState.update{it.copy(dueDate=event.value)}; is EditTaskUiEvent.PriorityChanged -> _uiState.update{it.copy(priority=event.value)}; EditTaskUiEvent.Save -> save() } }
    private fun save() = viewModelScope.launch { val s=_uiState.value; _uiState.update{it.copy(isSaving=true)}; when(val r=updateTask(Task(s.id,s.title,s.description,DateUtils.parseOrToday(s.dueDate),s.priority,s.completed))){ is Result.Success -> _uiState.update{it.copy(isSaving=false,saved=true)}; is Result.Error -> _uiState.update{it.copy(isSaving=false,error=r.message)}; Result.Loading -> Unit } }
}
