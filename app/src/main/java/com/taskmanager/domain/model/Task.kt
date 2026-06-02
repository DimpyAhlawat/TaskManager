package com.taskmanager.domain.model

data class Task(
    val id: Long = 0,
    val title: String,
    val description: String,
    val dueDateMillis: Long,
    val priority: Priority,
    val isCompleted: Boolean = false,
    val updatedAtMillis: Long = System.currentTimeMillis()
)
