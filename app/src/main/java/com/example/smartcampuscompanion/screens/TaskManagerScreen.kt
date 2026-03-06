package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartcampuscompanion.data.db.TaskEntity
import com.example.smartcampuscompanion.ui.components.PrimaryButton
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerScreen(navController: NavController, viewModel: TaskViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var taskToEdit by remember { mutableStateOf<TaskEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task & Schedule Manager") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF599E29),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    taskToEdit = null
                    showAddDialog = true
                },
                containerColor = Color(0xFF015DB6),
                contentColor = Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Task")
            }
        }
    ) { padding ->
        if (tasks.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.List,
                        contentDescription = null,
                        modifier = Modifier.size(100.dp),
                        tint = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "No tasks yet",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Get started by adding your first task!",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks) { task ->
                    TaskItem(
                        task = task,
                        onEdit = {
                            taskToEdit = task
                            showAddDialog = true
                        },
                        onDelete = { viewModel.deleteTask(task.id) }
                    )
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }

        if (showAddDialog) {
            TaskDialog(
                task = taskToEdit,
                onDismiss = { showAddDialog = false },
                onConfirm = { title, desc, millis ->
                    viewModel.upsertTask(
                        id = taskToEdit?.id ?: 0,
                        title = title,
                        description = desc,
                        dueAtMillis = millis
                    )
                    showAddDialog = false
                }
            )
        }
    }
}

@Composable
fun TaskItem(
    task: TaskEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val sdf = SimpleDateFormat("MMM dd, yyyy - hh:mm a", Locale.getDefault())
    val dateString = sdf.format(Date(task.dueAtMillis))

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(6.dp)
                    .background(Color(0xFF015DB6))
            )
            
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = task.title, 
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold, 
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    if (task.description.isNotEmpty()) {
                        Text(
                            text = task.description, 
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Due: $dateString", 
                        style = MaterialTheme.typography.labelMedium,
                        color = Color(0xFF599E29),
                        fontWeight = FontWeight.SemiBold
                    )
                }
                
                Row {
                    IconButton(onClick = onEdit) {
                        Icon(Icons.Default.Edit, contentDescription = "Edit", tint = Color(0xFF1976D2))
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color(0xFFD32F2F))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDialog(
    task: TaskEntity?,
    onDismiss: () -> Unit,
    onConfirm: (String, String, Long) -> Unit
) {
    var title by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    
    val calendar = remember { 
        Calendar.getInstance().apply { 
            timeInMillis = task?.dueAtMillis ?: System.currentTimeMillis() 
        } 
    }
    
    var selectedDateMillis by remember { mutableLongStateOf(calendar.timeInMillis) }
    
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)
    val timePickerState = rememberTimePickerState(
        initialHour = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE)
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (task == null) "New Task" else "Edit Task", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title") },
                    leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null, tint = Color(0xFF015DB6)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = MaterialTheme.shapes.medium
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (Optional)") },
                    leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, tint = Color(0xFF015DB6)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                )
                
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = { showDatePicker = true },
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.medium,
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(brush = SolidColor(Color(0xFF015DB6)))
                    ) {
                        val sdf = SimpleDateFormat("MMM dd", Locale.getDefault())
                        Text(sdf.format(Date(selectedDateMillis)), color = Color(0xFF015DB6), fontSize = 12.sp)
                    }
                    OutlinedButton(
                        onClick = { showTimePicker = true },
                        modifier = Modifier.weight(1f),
                        shape = MaterialTheme.shapes.medium,
                        border = ButtonDefaults.outlinedButtonBorder(enabled = true).copy(brush = SolidColor(Color(0xFF015DB6)))
                    ) {
                        val sdf = SimpleDateFormat("hh:mm a", Locale.getDefault())
                        Text(sdf.format(Date(selectedDateMillis)), color = Color(0xFF015DB6), fontSize = 12.sp)
                    }
                }

                if (showDatePicker) {
                    DatePickerDialog(
                        onDismissRequest = { showDatePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                val selectedDate = datePickerState.selectedDateMillis ?: selectedDateMillis
                                val selCal = Calendar.getInstance().apply { timeInMillis = selectedDate }
                                val currentCal = Calendar.getInstance().apply { timeInMillis = selectedDateMillis }
                                
                                currentCal.set(Calendar.YEAR, selCal.get(Calendar.YEAR))
                                currentCal.set(Calendar.MONTH, selCal.get(Calendar.MONTH))
                                currentCal.set(Calendar.DAY_OF_MONTH, selCal.get(Calendar.DAY_OF_MONTH))
                                
                                selectedDateMillis = currentCal.timeInMillis
                                showDatePicker = false
                            }, colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF015DB6))) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showDatePicker = false },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFD32F2F))
                            ) { Text("Cancel") }
                        }
                    ) {
                        DatePicker(
                            state = datePickerState,
                            colors = DatePickerDefaults.colors(
                                selectedDayContainerColor = Color(0xFF015DB6),
                                todayContentColor = Color(0xFF015DB6)
                            )
                        )
                    }
                }

                if (showTimePicker) {
                    AlertDialog(
                        onDismissRequest = { showTimePicker = false },
                        confirmButton = {
                            TextButton(onClick = {
                                val currentCal = Calendar.getInstance().apply { timeInMillis = selectedDateMillis }
                                currentCal.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                                currentCal.set(Calendar.MINUTE, timePickerState.minute)
                                selectedDateMillis = currentCal.timeInMillis
                                showTimePicker = false
                            }, colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFF015DB6))) { Text("OK") }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = { showTimePicker = false },
                                colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFD32F2F))
                            ) { Text("Cancel") }
                        },
                        text = {
                            TimePicker(
                                state = timePickerState,
                                colors = TimePickerDefaults.colors(
                                    selectorColor = Color(0xFF015DB6),
                                    periodSelectorSelectedContainerColor = Color(0xFF015DB6).copy(alpha = 0.1f)
                                )
                            )
                        }
                    )
                }
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),
                        contentColor = Color.White
                    )
                ) {
                    Text("Cancel", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
                
                Button(
                    onClick = { if (title.isNotBlank()) onConfirm(title, description, selectedDateMillis) },
                    enabled = title.isNotBlank(),
                    modifier = Modifier
                        .weight(1f)
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF015DB6),
                        contentColor = Color.White
                    ),
                    shape = MaterialTheme.shapes.medium,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                ) {
                    Text("Save Task", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }
            }
        },
        dismissButton = null
    )
}
