package com.taskmanager

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.taskmanager.core.ui.TaskManagerTheme
import com.taskmanager.domain.model.Priority
import com.taskmanager.domain.model.Task
import com.taskmanager.presentation.tasklist.TaskListScreen
import com.taskmanager.presentation.tasklist.TaskListUiState
import org.junit.Rule
import org.junit.Test

class TaskListScreenTest {
    @get:Rule val composeRule = createComposeRule()
    @Test fun showsEmptyState() {
        composeRule.setContent { TaskManagerTheme { TaskListScreen(TaskListUiState(isLoading = false), {}, {}, {}) } }
        composeRule.onNodeWithText("No tasks found").assertIsDisplayed()
    }
    @Test fun showsTask() {
        composeRule.setContent { TaskManagerTheme { TaskListScreen(TaskListUiState(isLoading = false, tasks = listOf(Task(1,"Buy milk","",0, Priority.MEDIUM))), {}, {}, {}) } }
        composeRule.onNodeWithText("Buy milk").assertIsDisplayed()
    }
}
