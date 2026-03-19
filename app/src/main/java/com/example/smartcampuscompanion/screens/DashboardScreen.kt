package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.background
<<<<<<< HEAD
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartcampuscompanion.data.Announcement
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.navigation.Routes
<<<<<<< HEAD
import kotlinx.coroutines.launch
=======
import com.example.smartcampuscompanion.ui.theme.BluePrimary
import com.example.smartcampuscompanion.ui.theme.GreenPrimary
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel
>>>>>>> c761885fa3313dfbab815bf276d0533db1f8710c

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(controller: NavController) {

    val context = LocalContext.current
    val session = remember { SessionManager(context) }
    val username = session.getUsername()
    
    val gradient = listOf(Color(0xFFF1F8E9), Color(0xFFE8F5E9), Color(0xFFE3F2FD))

<<<<<<< HEAD
    // drawer state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = Color.White,
                modifier = Modifier.width(300.dp)
            ) {
                // drawer header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF599E29))
                        .padding(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Smart Campus",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = username,
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // list of
                NavigationDrawerItem(
                    label = { Text("Profile") },
                    selected = false,
                    icon = { Icon(Icons.Default.Person, contentDescription = null) },
                    onClick = {
                        scope.launch { drawerState.close() }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Task & Schedule Manager") },
                    selected = false,
                    icon = { Icon(Icons.Default.List, contentDescription = null) },
                    onClick = {
                        scope.launch { 
                            drawerState.close()
                            controller.navigate(Routes.TASK_MANAGER)
                        }
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        // Main Screen UI
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            "Dashboard",
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
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
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (username.lowercase() == "admin") BluePrimary else GreenPrimary
                        )
                    }
                    IconButton(
                        onClick = {
>>>>>>> c761885fa3313dfbab815bf276d0533db1f8710c
                            session.logout()
                            controller.navigate(Routes.LOGIN_REGISTER) {
                                popUpTo(0) { inclusive = true }
                            }
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
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(brush = Brush.verticalGradient(colors = gradient)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Welcome back,",
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = username,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF599E29),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Smart Campus Companion",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        Button(
                            onClick = { controller.navigate(Routes.TASK_MANAGER) },
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
    modifier: Modifier = Modifier, 
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
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
fun ModernAnnouncementCard(announcement: Announcement, onClick: () -> Unit) {
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
                text = announcement.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = announcement.content,
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
                    text = announcement.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Gray
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowForward,
                    contentDescription = "View",
                    tint = BluePrimary
                )
            }
        }
    }
}
