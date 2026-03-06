package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartcampuscompanion.data.Announcement
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.ui.theme.BluePrimary
import com.example.smartcampuscompanion.ui.theme.GreenPrimary
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(controller: NavController) {

    val context = LocalContext.current
    val session = remember { SessionManager(context) }
    val username = session.getUsername()
    
    val gradient = listOf(Color(0xFFF1F8E9), Color(0xFFE8F5E9), Color(0xFFE3F2FD))

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
