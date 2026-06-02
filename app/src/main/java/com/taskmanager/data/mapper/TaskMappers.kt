package com.taskmanager.data.mapper

import com.taskmanager.core.util.DateUtils
import com.taskmanager.data.local.TaskEntity
import com.taskmanager.data.remote.TaskDto
import com.taskmanager.domain.model.Priority
import com.taskmanager.domain.model.Task

fun TaskEntity.toDomain(): Task = Task(id, title, description, dueDateMillis, priority, isCompleted, updatedAtMillis)

fun Task.toEntity(): TaskEntity = TaskEntity(id, title.trim(), description.trim(), dueDateMillis, priority, isCompleted, System.currentTimeMillis())

fun TaskDto.toEntity(): TaskEntity = TaskEntity(
    id = id,
    title = title,
    description = "Imported from DummyJson for user $userId",
    dueDateMillis = DateUtils.todayEpochMillis(),
    priority = when ((id % 3).toInt()) { 0 -> Priority.HIGH; 1 -> Priority.MEDIUM; else -> Priority.LOW },
    isCompleted = completed,
    updatedAtMillis = System.currentTimeMillis()
)
