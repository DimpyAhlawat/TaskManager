package com.taskmanager.presentation.tasklist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.taskmanager.presentation.components.TaskCard

@Composable
fun TaskListRoute(onAddClick: () -> Unit, onTaskClick: (Long) -> Unit, viewModel: TaskListViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    TaskListScreen(state, viewModel::onEvent, onAddClick, onTaskClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(state: TaskListUiState, onEvent: (TaskListUiEvent) -> Unit, onAddClick: () -> Unit, onTaskClick: (Long) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.error) { state.error?.let { snackbarHostState.showSnackbar(it) } }
    Scaffold(
        topBar = { TopAppBar(title = { Text("Tasks") }) },
        floatingActionButton = { FloatingActionButton(onClick = onAddClick) { Text("+") } },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(value = state.query, onValueChange = { onEvent(TaskListUiEvent.SearchChanged(it)) }, label = { Text("Search tasks") }, modifier = Modifier.fillMaxWidth().padding(16.dp))
            androidx.compose.foundation.layout.Row(Modifier.padding(horizontal = 16.dp)) {
                TaskFilter.entries.forEach { filter ->
                    FilterChip(selected = state.filter == filter, onClick = { onEvent(TaskListUiEvent.FilterChanged(filter)) }, label = { Text(filter.name.lowercase().replaceFirstChar { it.titlecase() }) }, modifier = Modifier.padding(end = 8.dp))
                }
            }
            Button(onClick = { onEvent(TaskListUiEvent.Refresh) }, modifier = Modifier.padding(16.dp), enabled = !state.isRefreshing) { Text(if (state.isRefreshing) "Refreshing…" else "Pull to refresh") }
            when {
                state.isLoading -> Box(Modifier.fillMaxSize(), Alignment.Center) { CircularProgressIndicator() }
                state.visibleTasks.isEmpty() -> Box(Modifier.fillMaxSize(), Alignment.Center) { Text("No tasks found", style = MaterialTheme.typography.titleMedium) }
                else -> LazyColumn(contentPadding = PaddingValues(bottom = 96.dp)) {
                    items(state.visibleTasks, key = { it.id }) { task -> TaskCard(task = task, onClick = { onTaskClick(task.id) }) }
                }
            }
        }
    }
}
