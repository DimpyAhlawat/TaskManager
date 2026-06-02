package com.taskmanager.data.repository

import com.taskmanager.core.common.Result
import com.taskmanager.data.local.TaskDao
import com.taskmanager.data.mapper.toDomain
import com.taskmanager.data.mapper.toEntity
import com.taskmanager.data.remote.NetworkResult
import com.taskmanager.data.remote.TaskRemoteDataSource
import com.taskmanager.domain.model.Task
import com.taskmanager.domain.repository.TaskRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dao: TaskDao,
    private val remoteDataSource: TaskRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : TaskRepository {
    override fun observeTasks(): Flow<Result<List<Task>>> = dao.observeTasks()
        .map { Result.Success(it.map { entity -> entity.toDomain() }) as Result<List<Task>> }
        .catch { emit(Result.Error(it.message ?: "Unable to load tasks", it)) }

    override fun observeTaskById(id: Long): Flow<Result<Task?>> = dao.observeTaskById(id)
        .map { Result.Success(it?.toDomain()) as Result<Task?> }
        .catch { emit(Result.Error(it.message ?: "Unable to load task", it)) }

    override suspend fun refreshTasks(): Result<Unit> = withContext(ioDispatcher) {
        when (val result = remoteDataSource.fetchTasks()) {
            is NetworkResult.Success -> {
                dao.insertTasks(result.data.map { it.toEntity() })
                Result.Success(Unit)
            }
            is NetworkResult.Error -> Result.Error(result.message, result.throwable)
        }
    }

    override suspend fun addTask(task: Task): Result<Long> = withContext(ioDispatcher) {
        runCatching { Result.Success(dao.insertTask(task.toEntity())) }.getOrElse { Result.Error(it.message ?: "Unable to add task", it) }
    }

    override suspend fun updateTask(task: Task): Result<Unit> = withContext(ioDispatcher) {
        runCatching { dao.updateTask(task.toEntity()); Result.Success(Unit) }.getOrElse { Result.Error(it.message ?: "Unable to update task", it) }
    }

    override suspend fun deleteTask(id: Long): Result<Unit> = withContext(ioDispatcher) {
        runCatching { dao.deleteTask(id); Result.Success(Unit) }.getOrElse { Result.Error(it.message ?: "Unable to delete task", it) }
    }
}
