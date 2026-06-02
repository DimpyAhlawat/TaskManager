package com.taskmanager.data.remote

import com.google.gson.annotations.SerializedName

data class TaskListDto(@SerializedName("todos") val todos: List<TaskDto>)

data class TaskDto(
    val id: Long,
    @SerializedName("todo") val title: String,
    val completed: Boolean,
    val userId: Long
)
