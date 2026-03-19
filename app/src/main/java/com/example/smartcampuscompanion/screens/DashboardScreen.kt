package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Apartment
import androidx.compose.material.icons.outlined.Campaign
import androidx.compose.material.icons.outlined.CheckCircleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.data.db.TaskEntity
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.ui.theme.BluePrimary
import com.example.smartcampuscompanion.ui.theme.GreenPrimary
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(controller: NavController, taskViewModel: TaskViewModel) {
    val context = LocalContext.current
    val session = remember { SessionManager(context) }
    val username = session.getUsername()

    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())
    val recentTask = tasks.firstOrNull()

    val gradient = listOf(
        Color(0xFFF1F8E9),
        Color(0xFFE8F5E9),
        Color(0xFFE3F2FD)
    )

    Scaffold(
        modifier = Modifier.background(brush = Brush.verticalGradient(colors = gradient)),
        containerColor = Color.Transparent
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Welcome back,",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = username.replaceFirstChar { char -> char.uppercase() },
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (username.lowercase() == "admin") BluePrimary else GreenPrimary
                        )
                    }

                    IconButton(
                        onClick = {
                            session.logout()
                            controller.navigate(Routes.LOGIN_REGISTER) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Logout,
                            contentDescription = "Logout",
                            tint = BluePrimary
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        QuickActionCard(
                            title = "Campus Info",
                            icon = Icons.Outlined.Apartment,
                            color = GreenPrimary,
                            onClick = { controller.navigate(Routes.CAMPUS_INFO) }
                        )
                    }

                    item {
                        QuickActionCard(
                            title = "Task Manager",
                            icon = Icons.AutoMirrored.Outlined.Assignment,
                            color = Color(0xFF673AB7),
                            onClick = { controller.navigate(Routes.TASK_MANAGER) }
                        )
                    }

                    item {
                        QuickActionCard(
                            title = "Announcements",
                            icon = Icons.Outlined.Campaign,
                            color = BluePrimary,
                            onClick = { controller.navigate(Routes.ANNOUNCEMENTS) }
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Upcoming Task",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )

                if (recentTask != null) {
                    TaskPreviewCard(task = recentTask) {
                        controller.navigate(Routes.TASK_MANAGER)
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White.copy(alpha = 0.8f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircleOutline,
                                contentDescription = null,
                                tint = GreenPrimary
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = "No pending tasks. Great job!",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuickActionCard(
    title: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(140.dp)
            .shadow(8.dp, RoundedCornerShape(28.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = color)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Surface(
                shape = CircleShape,
                color = Color.White.copy(alpha = 0.9f),
                modifier = Modifier.size(44.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.padding(10.dp)
                )
            }

            Text(
                text = title,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}

@Composable
fun TaskPreviewCard(task: TaskEntity, onClick: () -> Unit) {
    val dateString = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
        .format(Date(task.dueAtMillis))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = task.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Due: $dateString",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Red
                )

                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
                    contentDescription = "View",
                    tint = BluePrimary
                )
            }
        }
    }
}