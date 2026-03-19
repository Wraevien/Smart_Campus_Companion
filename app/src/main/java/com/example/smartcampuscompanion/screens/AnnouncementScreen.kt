package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartcampuscompanion.composables.AnnouncementCard
import com.example.smartcampuscompanion.ui.theme.GreenPrimary
import com.example.smartcampuscompanion.ui.theme.BluePrimary
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(controller: NavController) {

    val viewModel: AnnouncementViewModel = viewModel()
    val announcements by viewModel.announcements.collectAsState()
    val unreadCount by viewModel.unreadCount.collectAsState()
    val gradient = listOf(Color.White, Color(0xFFF1F8E9))

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Announcements",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { controller.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BluePrimary, // Changed from GreenPrimary to BluePrimary (Requested)
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(brush = Brush.verticalGradient(colors = gradient))
        ) {
            if (unreadCount > 0) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = BluePrimary.copy(alpha = 0.1f),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            tint = BluePrimary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "You have $unreadCount unread announcement${if (unreadCount > 1) "s" else ""}",
                            fontSize = 14.sp,
                            color = BluePrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                Spacer(modifier = Modifier.height(8.dp))
            }

            if (announcements.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp),
                            tint = Color.LightGray.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "All caught up!",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = "No new announcements for now.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp)
                ) {
                    items(announcements, key = { it.id }) { announcement ->
                        AnnouncementCard(
                            announcement = announcement,
                            onMarkAsRead = { viewModel.markAsRead(it) }
                        )
                    }
                }
            }
        }
    }
}
