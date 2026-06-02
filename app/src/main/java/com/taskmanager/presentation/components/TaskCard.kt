package com.taskmanager.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.taskmanager.core.util.DateUtils
import com.taskmanager.domain.model.Task

@Composable
fun TaskCard(task: Task, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth().clickable(onClick = onClick).padding(horizontal = 16.dp, vertical = 6.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(task.title, style = MaterialTheme.typography.titleMedium, textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null)
            Text(task.description, style = MaterialTheme.typography.bodyMedium)
            Row {
                Text("Due ${DateUtils.format(task.dueDateMillis)}", style = MaterialTheme.typography.labelMedium)
                Spacer(Modifier.width(12.dp))
                Text(task.priority.name, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
