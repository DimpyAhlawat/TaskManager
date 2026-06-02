package com.taskmanager.presentation.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DashboardRoute(onTasksClick: () -> Unit, viewModel: DashboardViewModel = hiltViewModel()) { val state by viewModel.uiState.collectAsState(); DashboardScreen(state, onTasksClick) }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(state: DashboardUiState, onTasksClick: () -> Unit) { Scaffold(topBar={ TopAppBar(title={ Text("Dashboard") }) }) { padding -> Column(Modifier.padding(padding).padding(16.dp)) { Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(20.dp)) { Text("Total tasks: ${state.total}", style=MaterialTheme.typography.titleLarge); Text("Completed tasks: ${state.completed}"); Text("Pending tasks: ${state.pending}"); Text("Progress: ${state.progress}%"); LinearProgressIndicator(progress={ state.progress / 100f }, modifier=Modifier.fillMaxWidth().padding(top=12.dp)) } }; Button(onClick=onTasksClick, modifier=Modifier.fillMaxWidth().padding(top=24.dp)) { Text("View Tasks") } } } }
