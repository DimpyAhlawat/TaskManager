package com.taskmanager.domain.usecase

import com.taskmanager.core.common.Result
import com.taskmanager.domain.model.Task
import com.taskmanager.domain.repository.TaskRepository
import javax.inject.Inject

class UpdateTaskUseCase @Inject constructor(private val repository: TaskRepository) {
    suspend operator fun invoke(task: Task): Result<Unit> = when {
        task.title.isBlank() -> Result.Error("Title cannot be blank")
        else -> repository.updateTask(task)
    }
}
