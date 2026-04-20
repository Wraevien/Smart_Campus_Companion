package com.example.smartcampuscompanion.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartcampuscompanion.composables.AppTopBar
import com.example.smartcampuscompanion.data.db.TaskEntity
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

private val TaskAccent = androidx.compose.ui.graphics.Color(0xFF7C3AED)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskManagerScreen(navController: NavController, viewModel: TaskViewModel) {
    val tasks         by viewModel.tasks.collectAsState()
    var showAddDialog by remember { mutableStateOf(false) }
    var taskToEdit    by remember { mutableStateOf<TaskEntity?>(null) }
    val colors        = MaterialTheme.colorScheme

    Scaffold(
        containerColor       = colors.background,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick        = { taskToEdit = null; showAddDialog = true },
                containerColor = colors.primary,
                contentColor   = colors.onPrimary,
                shape          = RoundedCornerShape(20.dp),
                elevation      = FloatingActionButtonDefaults.elevation(6.dp),
                icon           = { Icon(Icons.Default.Add, "Add Task") },
                text           = { Text("New Task", fontWeight = FontWeight.Bold) },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(padding),
        ) {
            // ── Consistent top bar ────────────────────────────────────
            AppTopBar(
                title    = "Task Manager",
                subtitle = "${tasks.size} task${if (tasks.size != 1) "s" else ""}",
                onBack   = { navController.popBackStack() },
            )

            // ── Body ──────────────────────────────────────────────────
            if (tasks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier            = Modifier.padding(32.dp),
                    ) {
                        Surface(
                            modifier = Modifier.size(110.dp),
                            shape    = CircleShape,
                            color    = TaskAccent.copy(alpha = 0.10f),
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    Icons.AutoMirrored.Outlined.Assignment,
                                    null,
                                    modifier = Modifier.size(52.dp),
                                    tint     = TaskAccent.copy(alpha = 0.5f),
                                )
                            }
                        }
                        Spacer(Modifier.height(24.dp))
                        Text(
                            "Productive day?",
                            style      = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color      = colors.onBackground,
                            textAlign  = TextAlign.Center,
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Your task list is clear.\nTap \"New Task\" to add one.",
                            style     = MaterialTheme.typography.bodyMedium,
                            color     = colors.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier            = Modifier.fillMaxSize(),
                    contentPadding      = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    item {
                        Text(
                            "My Schedule",
                            style      = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color      = colors.onBackground,
                            modifier   = Modifier.padding(bottom = 4.dp),
                        )
                    }
                    items(tasks, key = { it.id }) { task ->
                        ModernTaskItem(
                            task     = task,
                            onEdit   = { taskToEdit = task; showAddDialog = true },
                            onDelete = { viewModel.deleteTask(task.id) },
                        )
                    }
                    item { Spacer(Modifier.height(100.dp)) }
                }
            }
        }

        if (showAddDialog) {
            ModernTaskDialog(
                task      = taskToEdit,
                onDismiss = { showAddDialog = false },
                onConfirm = { title, desc, millis ->
                    viewModel.upsertTask(
                        id           = taskToEdit?.id ?: 0,
                        title        = title,
                        description  = desc,
                        dueAtMillis  = millis,
                    )
                    showAddDialog = false
                },
            )
        }
    }
}

@Composable
fun ModernTaskItem(task: TaskEntity, onEdit: () -> Unit, onDelete: () -> Unit) {
    val colors     = MaterialTheme.colorScheme
    val dateString = SimpleDateFormat("MMM dd, yyyy • hh:mm a", Locale.getDefault()).format(Date(task.dueAtMillis))

    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .shadow(6.dp, RoundedCornerShape(20.dp)),
        colors    = CardDefaults.cardColors(containerColor = colors.surface),
        shape     = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Row(
            modifier          = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape    = RoundedCornerShape(14.dp),
                color    = TaskAccent.copy(alpha = 0.12f),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.CalendarToday, null, tint = TaskAccent, modifier = Modifier.size(24.dp))
                }
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    task.title,
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color      = colors.onSurface,
                )
                if (task.description.isNotEmpty()) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        task.description,
                        style    = MaterialTheme.typography.bodySmall,
                        color    = colors.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
                Spacer(Modifier.height(6.dp))
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = colors.errorContainer,
                ) {
                    Row(
                        modifier          = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Outlined.Schedule, null, modifier = Modifier.size(12.dp), tint = colors.error)
                        Spacer(Modifier.width(4.dp))
                        Text(dateString, style = MaterialTheme.typography.labelSmall, color = colors.error, fontWeight = FontWeight.Medium)
                    }
                }
            }
            Column(horizontalAlignment = Alignment.End) {
                IconButton(onClick = onEdit)   { Icon(Icons.Outlined.Edit,   "Edit",   tint = colors.onSurfaceVariant) }
                IconButton(onClick = onDelete) { Icon(Icons.Outlined.Delete, "Delete", tint = colors.error) }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModernTaskDialog(
    task      : TaskEntity?,
    onDismiss : () -> Unit,
    onConfirm : (String, String, Long) -> Unit,
) {
    var title       by remember { mutableStateOf(task?.title ?: "") }
    var description by remember { mutableStateOf(task?.description ?: "") }
    var titleError  by remember { mutableStateOf(false) }
    val colors      = MaterialTheme.colorScheme

    val calendar = remember {
        Calendar.getInstance().apply {
            timeInMillis = task?.dueAtMillis ?: System.currentTimeMillis()
        }
    }
    var selectedDateMillis by remember { mutableLongStateOf(calendar.timeInMillis) }
    var showDatePicker     by remember { mutableStateOf(false) }
    var showTimePicker     by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = selectedDateMillis)
    val timePickerState = rememberTimePickerState(
        initialHour   = calendar.get(Calendar.HOUR_OF_DAY),
        initialMinute = calendar.get(Calendar.MINUTE),
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        properties       = androidx.compose.ui.window.DialogProperties(usePlatformDefaultWidth = false),
        content = {
            Surface(
                modifier        = Modifier
                    .fillMaxWidth(0.92f)
                    .wrapContentHeight(),
                shape           = RoundedCornerShape(28.dp),
                color           = colors.surface,
                tonalElevation  = 6.dp,
            ) {
                Column(
                    modifier            = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    Text(
                        if (task == null) "Create Task" else "Update Task",
                        style      = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color      = colors.onSurface,
                    )
                    OutlinedTextField(
                        value         = title,
                        onValueChange = { title = it; titleError = false },
                        label         = { Text("What needs to be done?") },
                        isError       = titleError,
                        supportingText = if (titleError) ({ Text("Title is required", color = colors.error) }) else null,
                        modifier       = Modifier.fillMaxWidth(),
                        shape          = RoundedCornerShape(14.dp),
                        singleLine     = true,
                        colors         = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = colors.primary,
                            unfocusedBorderColor = colors.outline,
                        ),
                    )
                    OutlinedTextField(
                        value         = description,
                        onValueChange = { description = it },
                        label         = { Text("Details (Optional)") },
                        modifier      = Modifier.fillMaxWidth(),
                        shape         = RoundedCornerShape(14.dp),
                        minLines      = 3,
                        colors        = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor   = colors.primary,
                            unfocusedBorderColor = colors.outline,
                        ),
                    )
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        Surface(
                            modifier       = Modifier.weight(1f).clickable { showDatePicker = true },
                            shape          = RoundedCornerShape(14.dp),
                            color          = colors.surfaceVariant,
                            tonalElevation = 1.dp,
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.CalendarToday, null, tint = colors.primary, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    SimpleDateFormat("MMM dd", Locale.getDefault()).format(Date(selectedDateMillis)),
                                    style      = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color      = colors.onSurface,
                                )
                            }
                        }
                        Surface(
                            modifier       = Modifier.weight(1f).clickable { showTimePicker = true },
                            shape          = RoundedCornerShape(14.dp),
                            color          = colors.surfaceVariant,
                            tonalElevation = 1.dp,
                        ) {
                            Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Outlined.Schedule, null, tint = colors.primary, modifier = Modifier.size(18.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date(selectedDateMillis)),
                                    style      = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color      = colors.onSurface,
                                )
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(
                        modifier              = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                    ) {
                        TextButton(onClick = onDismiss, modifier = Modifier.weight(1f)) {
                            Text("Cancel", color = colors.error, fontWeight = FontWeight.Bold)
                        }
                        Button(
                            onClick = {
                                if (title.isBlank()) { titleError = true; return@Button }
                                onConfirm(title, description, selectedDateMillis)
                            },
                            modifier = Modifier.weight(1.5f).height(50.dp),
                            shape    = RoundedCornerShape(14.dp),
                            colors   = ButtonDefaults.buttonColors(containerColor = colors.primary),
                        ) {
                            Text("Confirm", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        },
    )

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton    = {
                TextButton(onClick = {
                    val sel    = datePickerState.selectedDateMillis ?: selectedDateMillis
                    val selCal = Calendar.getInstance().apply { timeInMillis = sel }
                    val cur    = Calendar.getInstance().apply { timeInMillis = selectedDateMillis }
                    cur.set(Calendar.YEAR,         selCal.get(Calendar.YEAR))
                    cur.set(Calendar.MONTH,        selCal.get(Calendar.MONTH))
                    cur.set(Calendar.DAY_OF_MONTH, selCal.get(Calendar.DAY_OF_MONTH))
                    selectedDateMillis = cur.timeInMillis
                    showDatePicker = false
                }) { Text("OK", color = colors.primary) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel", color = colors.onSurfaceVariant)
                }
            },
        ) { DatePicker(state = datePickerState) }
    }

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton    = {
                TextButton(onClick = {
                    val cur = Calendar.getInstance().apply { timeInMillis = selectedDateMillis }
                    cur.set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    cur.set(Calendar.MINUTE,      timePickerState.minute)
                    selectedDateMillis = cur.timeInMillis
                    showTimePicker = false
                }) { Text("OK", color = colors.primary) }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("Cancel", color = colors.onSurfaceVariant)
                }
            },
            text = { TimePicker(state = timePickerState) },
        )
    }
}