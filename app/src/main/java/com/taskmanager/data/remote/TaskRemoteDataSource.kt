package com.taskmanager.data.remote

import javax.inject.Inject

class TaskRemoteDataSource @Inject constructor(private val api: TaskApi) {
    suspend fun fetchTasks(): NetworkResult<List<TaskDto>> = runCatching {
        val response = api.getTasks()
        if (response.isSuccessful) {
            NetworkResult.Success(response.body()?.todos ?: emptyList())
        } else {
            NetworkResult.Error(code = response.code(), message = response.message())
        }
    }.getOrElse { throwable ->
        NetworkResult.Error(message = throwable.message ?: "Network request failed", throwable = throwable)
    }
}
