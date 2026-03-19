package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.background
<<<<<<< HEAD
<<<<<<< HEAD
=======
import androidx.compose.foundation.clickable
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
<<<<<<< HEAD
import androidx.compose.material.icons.filled.*
=======
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
>>>>>>> c761885fa3313dfbab815bf276d0533db1f8710c
=======
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.*
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
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
import androidx.compose.ui.unit.dp
<<<<<<< HEAD
import androidx.lifecycle.viewmodel.compose.viewModel
=======
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
import androidx.navigation.NavController
import com.example.smartcampuscompanion.data.Announcement
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.navigation.Routes
<<<<<<< HEAD
<<<<<<< HEAD
import kotlinx.coroutines.launch
=======
import com.example.smartcampuscompanion.ui.theme.BluePrimary
import com.example.smartcampuscompanion.ui.theme.GreenPrimary
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel
>>>>>>> c761885fa3313dfbab815bf276d0533db1f8710c
=======
import com.example.smartcampuscompanion.ui.theme.BluePrimary
import com.example.smartcampuscompanion.ui.theme.GreenPrimary
import com.example.smartcampuscompanion.viewmodel.TaskViewModel
import com.example.smartcampuscompanion.data.db.TaskEntity
import java.text.SimpleDateFormat
import java.util.*
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(controller: NavController, taskViewModel: TaskViewModel) {

    val context = LocalContext.current
    val session = remember { SessionManager(context) }
    val username = session.getUsername()
    
    val gradient = listOf(Color(0xFFF1F8E9), Color(0xFFE8F5E9), Color(0xFFE3F2FD))

<<<<<<< HEAD
<<<<<<< HEAD
    // drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
=======
    // Observe tasks from the provided ViewModel
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())
    val recentTask = tasks.firstOrNull()
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9

    Scaffold(
        modifier = Modifier.background(brush = Brush.verticalGradient(colors = gradient)),
        containerColor = Color.Transparent
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header: Welcome back, User + Logout
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
<<<<<<< HEAD
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Open Drawer"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
=======
    val announcementViewModel: AnnouncementViewModel = viewModel()
    val announcements by announcementViewModel.announcements.collectAsState()
    val recentAnnouncement = announcements.firstOrNull()

    Scaffold(
        modifier = Modifier.background(brush = Brush.verticalGradient(colors = gradient)),
        containerColor = Color.Transparent
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            // Custom Header
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
                            text = username.replaceFirstChar { it.uppercase() },
=======
                        Text(
                            text = username.replaceFirstChar { char -> char.uppercase() },
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (username.lowercase() == "admin") BluePrimary else GreenPrimary
                        )
                    }
                    IconButton(
                        onClick = {
<<<<<<< HEAD
>>>>>>> c761885fa3313dfbab815bf276d0533db1f8710c
=======
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
                            session.logout()
                            controller.navigate(Routes.LOGIN_REGISTER) {
                                popUpTo(0) { inclusive = true }
                            }
<<<<<<< HEAD
<<<<<<< HEAD
                        }) {
                            Icon(
                                imageVector = Icons.Default.ExitToApp,
                                contentDescription = "Logout"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF599E29),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    )
                )
=======
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
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
            }

            // Quick Actions Section (LazyRow for scrolling)
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
                            color = Color(0xFF673AB7), // Purple for tasks
                            onClick = { controller.navigate(Routes.TASK_MANAGER) }
                        )
                    }
                }
            }
            
            // Recent Task Section
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
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
<<<<<<< HEAD
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text("Manage Tasks & Schedule", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = { controller.navigate(Routes.CAMPUS_INFO) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF599E29),
                                contentColor = Color.White
                            ),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 8.dp
                            )
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text("Campus Information", fontSize = 16.sp, fontWeight = FontWeight.Medium)
=======
                        },
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.5f))
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Logout,
                            contentDescription = "Logout",
                            tint = BluePrimary
                        )
                    }
                }
            }

            // Quick Actions Section
            item {
                Text(
                    text = "Quick Actions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    QuickActionCard(
                        title = "Campus Info",
                        icon = Icons.Outlined.Apartment,
                        color = GreenPrimary,
                        modifier = Modifier.weight(1f),
                        onClick = { controller.navigate(Routes.CAMPUS_INFO) }
                    )
                    QuickActionCard(
                        title = "Announcements",
                        icon = Icons.Outlined.Campaign,
                        color = BluePrimary,
                        modifier = Modifier.weight(1f),
                        onClick = { controller.navigate(Routes.ANNOUNCEMENTS) }
                    )
                }
            }
            
            // Recent Announcement Section
            item {
                Text(
                    text = "Recent Announcement",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
                )
                if (recentAnnouncement != null) {
                    ModernAnnouncementCard(announcement = recentAnnouncement) {
                        controller.navigate(Routes.ANNOUNCEMENTS)
                    }
                } else {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        shape = RoundedCornerShape(28.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(24.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Outlined.Done, contentDescription = null, tint = GreenPrimary)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("All caught up! No new announcements.", style = MaterialTheme.typography.bodyMedium)
>>>>>>> c761885fa3313dfbab815bf276d0533db1f8710c
=======
                            Icon(Icons.Outlined.CheckCircleOutline, contentDescription = null, tint = GreenPrimary)
                            Spacer(modifier = Modifier.width(16.dp))
                            Text("No pending tasks. Great job!", style = MaterialTheme.typography.bodyMedium)
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
                        }
                    }
                }
            }
        }
    }
}

@Composable
<<<<<<< HEAD
fun QuickActionCard(
    title: String, 
    icon: ImageVector, 
    color: Color, 
    modifier: Modifier = Modifier, 
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
=======
fun QuickActionCard(title: String, icon: ImageVector, color: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
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
<<<<<<< HEAD
                fontWeight = FontWeight.Bold,
                maxLines = 1
=======
                fontWeight = FontWeight.Bold
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
            )
        }
    }
}

@Composable
<<<<<<< HEAD
fun ModernAnnouncementCard(announcement: Announcement, onClick: () -> Unit) {
=======
fun TaskPreviewCard(task: TaskEntity, onClick: () -> Unit) {
    val dateString = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date(task.dueAtMillis))
    
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
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
<<<<<<< HEAD
                text = announcement.title,
=======
                text = task.title,
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
<<<<<<< HEAD
                text = announcement.content,
=======
                text = task.description,
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                maxLines = 2,
                overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
<<<<<<< HEAD
                    text = announcement.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowForward,
=======
                    text = "Due: $dateString",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Red
                )
                Icon(
                    imageVector = Icons.AutoMirrored.Outlined.ArrowForward,
>>>>>>> 36dc1132ec5bfba3f961199382ddee3856d3c2e9
                    contentDescription = "View",
                    tint = BluePrimary
                )
            }
        }
    }
}
