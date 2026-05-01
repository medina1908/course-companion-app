package com.example.coursecompanionapp.presentation.ui.screen.tasks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.coursecompanionapp.R
import com.example.coursecompanionapp.model.HardcodedData
import com.example.coursecompanionapp.model.Task
import com.example.coursecompanionapp.presentation.theme.CourseCompanionAppTheme
import com.example.coursecompanionapp.presentation.ui.screen.tasks.component.TaskItem
import com.example.coursecompanionapp.presentation.viewmodel.tasks.TasksUiState
import com.example.coursecompanionapp.presentation.viewmodel.tasks.TasksViewModel

private fun isTaskFormValid(title: String, dueDate: String) =
    title.isNotBlank() && dueDate.isNotBlank()

// STATEFUL
@Composable
fun TasksScreen(
    viewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val tasks by viewModel.tasks.collectAsStateWithLifecycle()

    var showForm by remember { mutableStateOf(false) }
    var taskTitle by remember { mutableStateOf("") }
    var taskDueDate by remember { mutableStateOf("") }

    val completedCount = tasks.count { it.isCompleted }
    val pendingCount = tasks.count { !it.isCompleted }
    val completionRate = if (tasks.isEmpty()) 0f
    else completedCount.toFloat() / tasks.size.toFloat()

    when (uiState) {
        is TasksUiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is TasksUiState.Error -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = (uiState as TasksUiState.Error).message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Button(onClick = { viewModel.resetUiState() }) {
                        Text("Try Again")
                    }
                }
            }
        }
        else -> {
            TasksScreen(
                tasks = tasks,
                showForm = showForm,
                taskTitle = taskTitle,
                taskDueDate = taskDueDate,
                completedCount = completedCount,
                pendingCount = pendingCount,
                completionRate = completionRate,
                onShowFormToggle = { showForm = !showForm },
                onTaskTitleChange = { taskTitle = it },
                onTaskDueDateChange = { taskDueDate = it },
                onSaveTask = {
                    if (isTaskFormValid(taskTitle, taskDueDate)) {
                        viewModel.addTask(
                            Task(
                                id = tasks.size + 1,
                                title = taskTitle,
                                courseId = 1,
                                dueDate = taskDueDate,
                                isCompleted = false
                            )
                        )
                        taskTitle = ""
                        taskDueDate = ""
                        showForm = false
                    }
                },
                onToggleTask = { viewModel.toggleTask(it) },
                modifier = modifier
            )
        }
    }
}

// STATELESS
@Composable
private fun TasksScreen(
    tasks: List<Task>,
    showForm: Boolean,
    taskTitle: String,
    taskDueDate: String,
    completedCount: Int,
    pendingCount: Int,
    completionRate: Float,
    onShowFormToggle: () -> Unit,
    onTaskTitleChange: (String) -> Unit,
    onTaskDueDateChange: (String) -> Unit,
    onSaveTask: () -> Unit,
    onToggleTask: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onShowFormToggle,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    if (showForm) "✕" else "+",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .background(Color(0xFFF5F7FA)),
            contentPadding = PaddingValues(bottom = dimensionResource(R.dimen.padding_large))
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.tertiary
                                )
                            )
                        )
                        .padding(dimensionResource(R.dimen.padding_large))
                ) {
                    Column {
                        Text(
                            text = "Hi Medina,",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = "You have $pendingCount pending, $completedCount completed!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            StatBox("Pending", pendingCount.toString())
                            StatBox("Completed", completedCount.toString())
                            StatBox("Total", tasks.size.toString())
                        }
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                        Text(
                            text = "Completion: ${(completionRate * 100).toInt()}%",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            if (showForm) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_medium))
                    ) {
                        Text(
                            text = "Add New Task",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = taskTitle,
                            onValueChange = onTaskTitleChange,
                            label = { Text("Task Title") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        OutlinedTextField(
                            value = taskDueDate,
                            onValueChange = onTaskDueDateChange,
                            label = { Text("Due Date (dd.mm.yyyy)") },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                        if (taskTitle.isNotBlank() && taskDueDate.isBlank()) {
                            Text(
                                text = "Please enter due date!",
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }

                        Button(
                            onClick = onSaveTask,
                            enabled = isTaskFormValid(taskTitle, taskDueDate),
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Save Task")
                        }

                        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                    }
                }
            }

            if (tasks.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(dimensionResource(R.dimen.padding_large)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tasks yet! Tap + to add your first task.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            } else {
                item {
                    Text(
                        text = "| My Tasks",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
                    )
                }
                items(
                    items = tasks,
                    key = { task -> task.id }
                ) { task ->
                    TaskItem(
                        task = task,
                        onToggle = onToggleTask,
                        modifier = Modifier.padding(
                            horizontal = dimensionResource(R.dimen.padding_medium),
                            vertical = dimensionResource(R.dimen.padding_small)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun StatBox(
    title: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TasksScreenPreview() {
    CourseCompanionAppTheme {
        TasksScreen(
            tasks = HardcodedData.tasks,
            showForm = false,
            taskTitle = "",
            taskDueDate = "",
            completedCount = 1,
            pendingCount = 2,
            completionRate = 0.33f,
            onShowFormToggle = {},
            onTaskTitleChange = {},
            onTaskDueDateChange = {},
            onSaveTask = {},
            onToggleTask = {}
        )
    }
}