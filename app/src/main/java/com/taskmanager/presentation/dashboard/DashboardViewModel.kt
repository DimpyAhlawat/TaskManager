package com.taskmanager.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmanager.core.common.Result
import com.taskmanager.domain.usecase.GetTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(getTasks: GetTasksUseCase) : ViewModel() { private val _uiState= MutableStateFlow(DashboardUiState()); val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow(); init { viewModelScope.launch { getTasks().collect { if (it is Result.Success) { val completed=it.data.count { t -> t.isCompleted }; _uiState.value=DashboardUiState(false,it.data.size,completed,it.data.size-completed) } } } } }
