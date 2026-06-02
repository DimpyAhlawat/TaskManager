package com.taskmanager

import app.cash.turbine.test
import com.taskmanager.data.local.TaskDao
import com.taskmanager.data.local.TaskEntity
import com.taskmanager.data.remote.NetworkResult
import com.taskmanager.data.remote.TaskRemoteDataSource
import com.taskmanager.data.repository.TaskRepositoryImpl
import com.taskmanager.domain.model.Priority
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskRepositoryImplTest {
    private val dao = mockk<TaskDao>(relaxed = true)
    private val remote = mockk<TaskRemoteDataSource>()
    private val dispatcher = StandardTestDispatcher()
    private val repository = TaskRepositoryImpl(dao, remote, dispatcher)

    @Test fun observeTasksEmitsLocalTasks() = runTest(dispatcher) {
        every { dao.observeTasks() } returns MutableStateFlow(listOf(TaskEntity(1,"Title","Description",0, Priority.HIGH,false,0)))
        repository.observeTasks().test { assertEquals("Title", (awaitItem() as com.taskmanager.core.common.Result.Success).data.first().title); cancelAndIgnoreRemainingEvents() }
    }

    @Test fun refreshStoresRemoteTasks() = runTest(dispatcher) {
        coEvery { remote.fetchTasks() } returns NetworkResult.Success(emptyList())
        repository.refreshTasks()
        coVerify { dao.insertTasks(emptyList()) }
    }
}
