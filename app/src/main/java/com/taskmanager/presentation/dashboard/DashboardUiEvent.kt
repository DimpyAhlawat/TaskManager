package com.taskmanager.presentation.dashboard

sealed interface DashboardUiEvent { data object Refresh: DashboardUiEvent }
