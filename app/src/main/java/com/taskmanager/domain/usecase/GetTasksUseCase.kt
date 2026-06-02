package com.taskmanager.domain.usecase

import com.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class GetTasksUseCase @Inject constructor(private val repository: TaskRepository) {
    operator fun invoke() = repository.observeTasks()
}
