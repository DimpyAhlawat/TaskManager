package com.taskmanager.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun TaskManagerTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = lightColorScheme(), typography = androidx.compose.material3.Typography(), content = content)
}
