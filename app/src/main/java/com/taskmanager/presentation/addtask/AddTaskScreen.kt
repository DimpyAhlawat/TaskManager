package com.taskmanager.presentation.addtask

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.padding
import androidx.hilt.navigation.compose.hiltViewModel
import com.taskmanager.presentation.components.TaskForm

@Composable
fun AddTaskRoute(onSaved: () -> Unit, viewModel: AddTaskViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(state.saved) { if (state.saved) onSaved() }
    AddTaskScreen(state, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(state: AddTaskUiState, onEvent: (AddTaskUiEvent) -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("Add Task") }) }) { padding ->
        TaskForm(state.title, state.description, state.dueDate, state.priority, state.isSaving, { onEvent(AddTaskUiEvent.TitleChanged(it)) }, { onEvent(AddTaskUiEvent.DescriptionChanged(it)) }, { onEvent(AddTaskUiEvent.DueDateChanged(it)) }, { onEvent(AddTaskUiEvent.PriorityChanged(it)) }, { onEvent(AddTaskUiEvent.Save) }, "Save Task", modifier = Modifier.padding(padding))
    }
}
