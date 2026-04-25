package com.example.smartcampuscompanion.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.data.UserRole
import com.example.smartcampuscompanion.data.Task
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun DashboardScreen(
    controller:    NavController,
    taskViewModel: TaskViewModel,
) {
    val context     = LocalContext.current
    val session     = remember { SessionManager(context) }
    val username    = session.getUsername()
    val uid         = session.getUid()
    val isAdmin     = session.getRole() == UserRole.ADMIN

    val tasks       by taskViewModel.tasks.collectAsState(initial = emptyList())
    val announcementVm: AnnouncementViewModel = viewModel()
    
    // Set current user for ViewModels to separate data
    LaunchedEffect(username, uid) {
        taskViewModel.setCurrentUser(uid)
        announcementVm.setCurrentUser(username)
    }

    val unreadCount by announcementVm.unreadCount.collectAsState()
    val recentTask  = tasks.firstOrNull()

    val colors = MaterialTheme.colorScheme

    val headerGradient = Brush.linearGradient(
        listOf(colors.primary, colors.primary.copy(alpha = 0.75f))
    )

    Scaffold(containerColor = colors.background) { pv ->
        LazyColumn(
            modifier       = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(pv),
            contentPadding = PaddingValues(bottom = 40.dp),
        ) {

            // ── Hero Header ───────────────────────────────────────────
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            headerGradient,
                            RoundedCornerShape(bottomStart = 36.dp, bottomEnd = 36.dp)
                        )
                        .statusBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                ) {
                    Column {
                        Row(
                            modifier              = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment     = Alignment.Top,
                        ) {
                            Column {
                                Text(
                                    text  = if (isAdmin) "Admin Panel" else "Smart Campus",
                                    style = MaterialTheme.typography.labelLarge,
                                    color = Color.White.copy(alpha = 0.70f),
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text       = "Hey, ${username.replaceFirstChar { it.uppercase() }} 👋",
                                    style      = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.ExtraBold,
                                    color      = Color.White,
                                )
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                // Profile
                                IconButton(
                                    onClick  = { controller.navigate(Routes.PROFILE) },
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.18f)),
                                ) {
                                    Icon(Icons.Outlined.Person, "Profile", tint = Color.White)
                                }
                                // Logout
                                IconButton(
                                    onClick  = {
                                        session.logout()
                                        controller.navigate(Routes.LOGIN_REGISTER) {
                                            popUpTo(0) { inclusive = true }
                                        }
                                    },
                                    modifier = Modifier
                                        .size(42.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.18f)),
                                ) {
                                    Icon(Icons.AutoMirrored.Outlined.Logout, "Logout", tint = Color.White)
                                }
                            }
                        }

                        Spacer(Modifier.height(20.dp))

                        // Role badge
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = Color.White.copy(alpha = 0.20f),
                        ) {
                            Row(
                                modifier          = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(
                                    if (isAdmin) Icons.Outlined.AdminPanelSettings else Icons.Outlined.School,
                                    null, tint = Color.White, modifier = Modifier.size(16.dp),
                                )
                                Spacer(Modifier.width(6.dp))
                                Text(
                                    if (isAdmin) "Administrator" else "Student",
                                    style      = MaterialTheme.typography.labelMedium,
                                    color      = Color.White,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        }

                        // Unread badge
                        if (unreadCount > 0) {
                            Spacer(Modifier.height(10.dp))
                            Surface(
                                shape    = RoundedCornerShape(50),
                                color    = Color.White.copy(alpha = 0.15f),
                                modifier = Modifier.clickable { controller.navigate(Routes.ANNOUNCEMENTS) },
                            ) {
                                Row(
                                    modifier          = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(Icons.Outlined.Notifications, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        "$unreadCount unread announcement${if (unreadCount > 1) "s" else ""}",
                                        style      = MaterialTheme.typography.labelMedium,
                                        color      = Color.White,
                                        fontWeight = FontWeight.SemiBold,
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Icon(Icons.AutoMirrored.Outlined.ArrowForward, null, tint = Color.White.copy(0.8f), modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    }
                }
            }

            // ── Quick Actions ─────────────────────────────────────────
            item {
                Spacer(Modifier.height(28.dp))
                SectionHeader("Quick Actions")
                Spacer(Modifier.height(14.dp))
                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                    item {
                        QuickActionCard(
                            title    = "Campus Info",
                            subtitle = "Departments & contacts",
                            icon     = Icons.Outlined.Apartment,
                            gradient = Brush.linearGradient(listOf(Color(0xFF065F46), Color(0xFF10B981))),
                            onClick  = { controller.navigate(Routes.CAMPUS_INFO) },
                        )
                    }
                    item {
                        QuickActionCard(
                            title    = "Task Manager",
                            subtitle = "Your to-dos",
                            icon     = Icons.AutoMirrored.Outlined.Assignment,
                            gradient = Brush.linearGradient(listOf(Color(0xFF4C1D95), Color(0xFF7C3AED))),
                            onClick  = { controller.navigate(Routes.TASK_MANAGER) },
                        )
                    }
                    item {
                        QuickActionCard(
                            title    = "Announcements",
                            subtitle = "$unreadCount unread",
                            icon     = Icons.Outlined.Campaign,
                            gradient = Brush.linearGradient(listOf(Color(0xFF1E40AF), Color(0xFF3B82F6))),
                            onClick  = { controller.navigate(Routes.ANNOUNCEMENTS) },
                        )
                    }
                    if (isAdmin) {
                        item {
                            QuickActionCard(
                                title    = "Post Notice",
                                subtitle = "New announcement",
                                icon     = Icons.Outlined.Edit,
                                gradient = Brush.linearGradient(listOf(Color(0xFF92400E), Color(0xFFD97706))),
                                onClick  = { controller.navigate(Routes.POST_ANNOUNCEMENT) },
                            )
                        }
                    }
                }
            }

            // ── Admin stats ───────────────────────────────────────────
            if (isAdmin) {
                item {
                    Spacer(Modifier.height(28.dp))
                    SectionHeader("Overview")
                    Spacer(Modifier.height(14.dp))
                    Row(
                        modifier              = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        StatCard(
                            modifier = Modifier.weight(1f),
                            label    = "Pending Tasks",
                            value    = tasks.size.toString(),
                            icon     = Icons.AutoMirrored.Outlined.Assignment,
                            color    = Color(0xFF7C3AED),
                        )
                        StatCard(
                            modifier = Modifier.weight(1f),
                            label    = "Unread Notices",
                            value    = unreadCount.toString(),
                            icon     = Icons.Outlined.Campaign,
                            color    = colors.primary,
                        )
                    }
                }
            }

            // ── Upcoming task ─────────────────────────────────────────
            item {
                Spacer(Modifier.height(28.dp))
                SectionHeader("Upcoming Task")
                Spacer(Modifier.height(14.dp))
                if (recentTask != null) {
                    TaskPreviewCard(task = recentTask) { controller.navigate(Routes.TASK_MANAGER) }
                } else {
                    EmptyTaskCard()
                }
            }
        }
    }
}

// ── Shared sub-composables ────────────────────────────────────────────────────

@Composable
fun SectionHeader(title: String) {
    Text(
        text       = title,
        style      = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        modifier   = Modifier.padding(horizontal = 24.dp),
        color      = MaterialTheme.colorScheme.onBackground,
    )
}

@Composable
fun QuickActionCard(
    title    : String,
    subtitle : String,
    icon     : ImageVector,
    gradient : Brush,
    onClick  : () -> Unit,
) {
    Card(
        modifier = Modifier
            .width(155.dp)
            .height(145.dp)
            .shadow(8.dp, RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
        shape  = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(18.dp),
        ) {
            Column(
                modifier            = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Surface(
                    shape    = CircleShape,
                    color    = Color.White.copy(alpha = 0.22f),
                    modifier = Modifier.size(44.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, null, tint = Color.White, modifier = Modifier.size(24.dp))
                    }
                }
                Column {
                    Text(title,    color = Color.White, style = MaterialTheme.typography.titleSmall,  fontWeight = FontWeight.Bold, maxLines = 1)
                    Text(subtitle, color = Color.White.copy(0.75f), style = MaterialTheme.typography.labelSmall, maxLines = 1)
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    modifier : Modifier,
    label    : String,
    value    : String,
    icon     : ImageVector,
    color    : Color,
) {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier  = modifier.shadow(4.dp, RoundedCornerShape(20.dp)),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(0.dp),
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Surface(
                shape    = CircleShape,
                color    = color.copy(alpha = 0.12f),
                modifier = Modifier.size(40.dp),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = color, modifier = Modifier.size(22.dp))
                }
            }
            Spacer(Modifier.height(12.dp))
            Text(value, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold, color = color)
            Text(label, style = MaterialTheme.typography.labelSmall, color = colors.onSurfaceVariant)
        }
    }
}

@Composable
private fun TaskPreviewCard(task: Task, onClick: () -> Unit) {
    val colors     = MaterialTheme.colorScheme
    val taskColor  = Color(0xFF7C3AED)
    val dateString = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date(task.dueAtMillis))
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clickable(onClick = onClick),
        shape     = RoundedCornerShape(24.dp),
        colors    = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Column(modifier = Modifier.padding(22.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape    = CircleShape,
                    color    = taskColor.copy(alpha = 0.10f),
                    modifier = Modifier.size(40.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.AutoMirrored.Outlined.Assignment, null, tint = taskColor, modifier = Modifier.size(22.dp))
                    }
                }
                Spacer(Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(task.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis, color = colors.onSurface)
                    if (task.description.isNotBlank()) {
                        Text(task.description, style = MaterialTheme.typography.bodySmall, color = colors.onSurfaceVariant, maxLines = 1, overflow = TextOverflow.Ellipsis)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = colors.outlineVariant.copy(alpha = 0.5f))
            Spacer(Modifier.height(14.dp))
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Surface(shape = RoundedCornerShape(8.dp), color = colors.errorContainer) {
                    Text("Due $dateString", style = MaterialTheme.typography.labelSmall, color = colors.error, modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                }
                Surface(shape = CircleShape, color = taskColor.copy(alpha = 0.10f), modifier = Modifier.size(32.dp)) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowForward, null, tint = taskColor, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyTaskCard() {
    val colors = MaterialTheme.colorScheme
    Card(
        modifier  = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        shape     = RoundedCornerShape(24.dp),
        colors    = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(2.dp),
    ) {
        Row(
            modifier          = Modifier.fillMaxWidth().padding(22.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(shape = CircleShape, color = colors.secondary.copy(alpha = 0.10f), modifier = Modifier.size(44.dp)) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Outlined.CheckCircle, null, tint = colors.secondary, modifier = Modifier.size(24.dp))
                }
            }
            Spacer(Modifier.width(16.dp))
            Column {
                Text("All caught up!", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.SemiBold, color = colors.onSurface)
                Text("No pending tasks. Great job!", style = MaterialTheme.typography.bodySmall, color = colors.onSurfaceVariant)
            }
        }
    }
}