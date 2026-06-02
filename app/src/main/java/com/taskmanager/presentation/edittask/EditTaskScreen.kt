package com.taskmanager.presentation.edittask

import androidx.compose.material3.CircularProgressIndicator
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
fun EditTaskRoute(onSaved: () -> Unit, viewModel: EditTaskViewModel = hiltViewModel()) { val state by viewModel.uiState.collectAsState(); LaunchedEffect(state.saved){ if(state.saved) onSaved() }; EditTaskScreen(state, viewModel::onEvent) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTaskScreen(state: EditTaskUiState, onEvent: (EditTaskUiEvent) -> Unit) { Scaffold(topBar={ TopAppBar(title={ Text("Edit Task") }) }) { padding -> if(state.isLoading) CircularProgressIndicator(Modifier.padding(padding)) else TaskForm(state.title,state.description,state.dueDate,state.priority,state.isSaving,{onEvent(EditTaskUiEvent.TitleChanged(it))},{onEvent(EditTaskUiEvent.DescriptionChanged(it))},{onEvent(EditTaskUiEvent.DueDateChanged(it))},{onEvent(EditTaskUiEvent.PriorityChanged(it))},{onEvent(EditTaskUiEvent.Save)},"Save Changes", Modifier.padding(padding)) } }
