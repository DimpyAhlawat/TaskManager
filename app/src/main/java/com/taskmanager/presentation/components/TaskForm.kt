package com.taskmanager.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.taskmanager.domain.model.Priority

@Composable
fun TaskForm(title: String, description: String, dueDate: String, priority: Priority, isSaving: Boolean, onTitle: (String)->Unit, onDescription: (String)->Unit, onDueDate: (String)->Unit, onPriority: (Priority)->Unit, onSave: ()->Unit, buttonText: String, modifier: Modifier = Modifier) {
    Column(modifier.padding(16.dp)) {
        OutlinedTextField(title, onTitle, Modifier.fillMaxWidth(), label = { Text("Title") })
        OutlinedTextField(description, onDescription, Modifier.fillMaxWidth().padding(top = 12.dp), label = { Text("Description") }, minLines = 3)
        OutlinedTextField(dueDate, onDueDate, Modifier.fillMaxWidth().padding(top = 12.dp), label = { Text("Due Date (yyyy-MM-dd)") })
        androidx.compose.foundation.layout.Row(Modifier.padding(top = 12.dp)) { Priority.entries.forEach { FilterChip(selected = priority == it, onClick = { onPriority(it) }, label = { Text(it.name) }, modifier = Modifier.padding(end = 8.dp)) } }
        Button(onClick = onSave, enabled = !isSaving, modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) { Text(if (isSaving) "Saving…" else buttonText) }
    }
}
