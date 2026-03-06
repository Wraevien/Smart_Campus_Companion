package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.ui.theme.GreenPrimary
import com.example.smartcampuscompanion.ui.theme.BluePrimary
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(controller: NavController) {

    val context = LocalContext.current
    val session = remember { SessionManager(context) }
    val username = session.getUsername()
    // Soft Green gradient background
    val gradient = listOf(Color(0xFFF1F8E9), Color(0xFFC5E1A5))

    val announcementViewModel: AnnouncementViewModel = viewModel()
    val unreadCount by announcementViewModel.unreadCount.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "DASHBOARD",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 2.sp
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            session.logout()
                            controller.navigate(Routes.LOGIN_REGISTER) {
                                popUpTo(0) { inclusive = true }
                            }
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f))
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = BluePrimary, // Changed to Blue (Requested)
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(brush = Brush.verticalGradient(colors = gradient))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Main Profile Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(15.dp, RoundedCornerShape(32.dp)),
                    shape = RoundedCornerShape(32.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp, horizontal = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = BluePrimary.copy(alpha = 0.1f), // Switched to Blue for avatar
                            modifier = Modifier.size(90.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = BluePrimary,
                                modifier = Modifier.padding(18.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Welcome back,",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Normal
                        )

                        Text(
                            text = username.replaceFirstChar { it.uppercase() },
                            style = MaterialTheme.typography.headlineLarge,
                            fontWeight = FontWeight.Medium,
                            color = BluePrimary,
                            modifier = Modifier.padding(vertical = 4.dp),
                            letterSpacing = (-0.5).sp
                        )

                        Text(
                            text = "Smart Campus Companion",
                            style = MaterialTheme.typography.labelLarge,
                            color = Color.LightGray.copy(alpha = 0.8f),
                            letterSpacing = 1.2.sp,
                            fontWeight = FontWeight.Normal
                        )
                    }
                }

                // Action Buttons Section - Changed to Blue (Requested)
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    AestheticButton(
                        text = "Campus Information",
                        icon = Icons.Default.Info,
                        backgroundColor = BluePrimary,
                        onClick = { controller.navigate(Routes.CAMPUS_INFO) }
                    )

                    AestheticButton(
                        text = "Announcements",
                        icon = Icons.Default.Notifications,
                        backgroundColor = BluePrimary,
                        badgeCount = unreadCount,
                        onClick = { controller.navigate(Routes.ANNOUNCEMENTS) }
                    )
                }
            }
        }
    }
}

@Composable
fun AestheticButton(
    text: String,
    icon: ImageVector,
    backgroundColor: Color,
    badgeCount: Int = 0,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .shadow(10.dp, RoundedCornerShape(24.dp)),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = Color.White
        ),
        contentPadding = PaddingValues(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color.White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(10.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(20.dp))
            
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.weight(1f),
                letterSpacing = 0.5.sp
            )
            
            if (badgeCount > 0) {
                Surface(
                    color = Color.White,
                    shape = CircleShape,
                    modifier = Modifier.size(28.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = badgeCount.toString(),
                            color = backgroundColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            } else {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}
