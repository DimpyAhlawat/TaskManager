package com.taskmanager

import com.taskmanager.core.common.Result
import com.taskmanager.domain.model.Priority
import com.taskmanager.domain.model.Task
import com.taskmanager.domain.usecase.GetTasksUseCase
import com.taskmanager.domain.usecase.RefreshTasksUseCase
import com.taskmanager.presentation.tasklist.TaskFilter
import com.taskmanager.presentation.tasklist.TaskListUiEvent
import com.taskmanager.presentation.tasklist.TaskListViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class TaskListViewModelTest {
    @get:Rule val mainDispatcherRule = MainDispatcherRule()
    @Test fun filtersCompletedTasks() {
        val getTasks = mockk<GetTasksUseCase>()
        val refresh = mockk<RefreshTasksUseCase>()
        every { getTasks() } returns flowOf(Result.Success(listOf(Task(1,"A","",0, Priority.LOW,true), Task(2,"B","",0, Priority.HIGH,false))))
        coEvery { refresh() } returns Result.Success(Unit)
        val viewModel = TaskListViewModel(getTasks, refresh)
        viewModel.onEvent(TaskListUiEvent.FilterChanged(TaskFilter.COMPLETED))
        assertEquals(1, viewModel.uiState.value.visibleTasks.size)
    }
}
