package com.taskmanager.domain.usecase

import com.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class GetTaskByIdUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke(id: Long) = repository.observeTaskById(id)
}
