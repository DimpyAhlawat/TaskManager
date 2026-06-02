package com.taskmanager.presentation.tasklist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.taskmanager.core.common.Result
import com.taskmanager.domain.usecase.GetTasksUseCase
import com.taskmanager.domain.usecase.RefreshTasksUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(
    getTasks: GetTasksUseCase,
    private val refreshTasks: RefreshTasksUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TaskListUiState())
    val uiState: StateFlow<TaskListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            getTasks().collect { result ->
                _uiState.update { state ->
                    when (result) {
                        is Result.Success -> state.copy(isLoading = false, tasks = result.data, error = null)
                        is Result.Error -> state.copy(isLoading = false, error = result.message)
                        Result.Loading -> state.copy(isLoading = true)
                    }
                }
            }
        }
    }

    fun onEvent(event: TaskListUiEvent) {
        when (event) {
            is TaskListUiEvent.SearchChanged -> _uiState.update { it.copy(query = event.query) }
            is TaskListUiEvent.FilterChanged -> _uiState.update { it.copy(filter = event.filter) }
            TaskListUiEvent.Refresh -> refresh()
        }
    }

    private fun refresh() = viewModelScope.launch {
        _uiState.update { it.copy(isRefreshing = true) }
        when (val result = refreshTasks()) {
            is Result.Error -> _uiState.update { it.copy(error = result.message) }
            else -> Unit
        }
        _uiState.update { it.copy(isRefreshing = false) }
    }
}
