package com.taskmanager.di

import com.taskmanager.data.repository.TaskRepositoryImpl
import com.taskmanager.domain.repository.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds @Singleton abstract fun bindTaskRepository(impl: TaskRepositoryImpl): TaskRepository
}
