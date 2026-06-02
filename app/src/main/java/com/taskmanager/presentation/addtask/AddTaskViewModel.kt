package com.taskmanager.presentation.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmanager.core.common.Result
import com.taskmanager.core.util.DateUtils
import com.taskmanager.domain.model.Task
import com.taskmanager.domain.usecase.AddTaskUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddTaskViewModel @Inject constructor(private val addTask: AddTaskUseCase) : ViewModel() {
    private val _uiState = MutableStateFlow(AddTaskUiState())
    val uiState: StateFlow<AddTaskUiState> = _uiState.asStateFlow()
    fun onEvent(event: AddTaskUiEvent) { when(event){
        is AddTaskUiEvent.TitleChanged -> _uiState.update{it.copy(title=event.value)}
        is AddTaskUiEvent.DescriptionChanged -> _uiState.update{it.copy(description=event.value)}
        is AddTaskUiEvent.DueDateChanged -> _uiState.update{it.copy(dueDate=event.value)}
        is AddTaskUiEvent.PriorityChanged -> _uiState.update{it.copy(priority=event.value)}
        AddTaskUiEvent.Save -> save()
    } }
    private fun save() = viewModelScope.launch { val s = _uiState.value; _uiState.update{it.copy(isSaving=true)}; when(val r = addTask(Task(title=s.title, description=s.description, dueDateMillis= DateUtils.parseOrToday(s.dueDate), priority=s.priority))){ is Result.Success -> _uiState.update{it.copy(isSaving=false, saved=true)}; is Result.Error -> _uiState.update{it.copy(isSaving=false, error=r.message)}; Result.Loading -> Unit } }
}
