package com.taskmanager.presentation.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.taskmanager.core.util.DateUtils

@Composable
fun TaskDetailRoute(onDeleted: () -> Unit, onEdit: (Long) -> Unit, viewModel: TaskDetailViewModel = hiltViewModel()) { val state by viewModel.uiState.collectAsState(); LaunchedEffect(state.deleted){ if(state.deleted) onDeleted() }; TaskDetailScreen(state, viewModel::onEvent, onEdit) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(state: TaskDetailUiState, onEvent: (TaskDetailUiEvent) -> Unit, onEdit: (Long) -> Unit) { Scaffold(topBar={ TopAppBar(title={ Text("Task Detail") }) }) { padding -> when { state.isLoading -> CircularProgressIndicator(Modifier.padding(padding)); state.task == null -> Text("Task not found", Modifier.padding(padding).padding(16.dp)); else -> { val task=state.task; Card(Modifier.padding(padding).padding(16.dp).fillMaxWidth()) { Column(Modifier.padding(16.dp)) { Text(task.title, style=MaterialTheme.typography.headlineSmall); Text(task.description, Modifier.padding(top=8.dp)); Text("Due: ${DateUtils.format(task.dueDateMillis)}"); Text("Priority: ${task.priority}"); Text("Status: ${if(task.isCompleted) "Completed" else "Pending"}"); Row(Modifier.padding(top=16.dp)) { Button({ onEvent(TaskDetailUiEvent.ToggleCompleted) }) { Text(if(task.isCompleted) "Mark pending" else "Mark completed") }; Button({ onEdit(task.id) }, Modifier.padding(start=8.dp)) { Text("Edit") }; Button({ onEvent(TaskDetailUiEvent.Delete) }, Modifier.padding(start=8.dp)) { Text("Delete") } } } } } } } }
