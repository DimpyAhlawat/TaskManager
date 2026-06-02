package com.taskmanager.data.remote

import retrofit2.Response
import retrofit2.http.GET

interface TaskApi {
    @GET("todos?limit=30")
    suspend fun getTasks(): Response<TaskListDto>
}
