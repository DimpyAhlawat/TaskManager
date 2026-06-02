package com.taskmanager.domain.usecase

import com.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class RefreshTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke() = repository.refreshTasks()
}
