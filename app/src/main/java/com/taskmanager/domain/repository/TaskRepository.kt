package com.taskmanager.domain.repository

import com.taskmanager.core.common.Result
import com.taskmanager.domain.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun observeTasks(): Flow<Result<List<Task>>>
    fun observeTaskById(id: Long): Flow<Result<Task?>>
    suspend fun refreshTasks(): Result<Unit>
    suspend fun addTask(task: Task): Result<Long>
    suspend fun updateTask(task: Task): Result<Unit>
    suspend fun deleteTask(id: Long): Result<Unit>
}
