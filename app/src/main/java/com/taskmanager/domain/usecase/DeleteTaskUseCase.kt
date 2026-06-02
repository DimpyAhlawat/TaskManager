package com.taskmanager.domain.usecase

import com.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class DeleteTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(id: Long) = repository.deleteTask(id)
}
