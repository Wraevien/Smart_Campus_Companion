package com.example.smartcampuscompanion.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartcampuscompanion.composables.AnnouncementCard
import com.example.smartcampuscompanion.composables.AppTopBar
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.data.UserRole
import com.example.smartcampuscompanion.navigation.Routes
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel

@Composable
fun AnnouncementsScreen(controller: NavController) {
    val context   = LocalContext.current
    val session   = remember { SessionManager(context) }
    val isAdmin   = session.getRole() == UserRole.ADMIN
    val colors    = MaterialTheme.colorScheme

    val viewModel     : AnnouncementViewModel = viewModel()
    val announcements by viewModel.announcements.collectAsState()
    val unreadCount   by viewModel.unreadCount.collectAsState()
    val isLoading     by viewModel.isLoading.collectAsState(initial = false)

    Scaffold(
        containerColor = colors.background,
        topBar = {
            AppTopBar(
                title    = "Announcements",
                subtitle = if (unreadCount > 0) "$unreadCount unread" else null,
                onBack   = { controller.popBackStack() },
                actions  = {
                    if (isAdmin) {
                        Surface(
                            onClick  = { controller.navigate(Routes.POST_ANNOUNCEMENT) },
                            shape    = RoundedCornerShape(50),
                            color    = Color.White.copy(alpha = 0.22f),
                        ) {
                            Row(
                                modifier          = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Icon(Icons.Outlined.Add, null, tint = Color.White, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    "Post",
                                    color      = Color.White,
                                    style      = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.SemiBold,
                                )
                            }
                        }
                        Spacer(Modifier.width(6.dp))
                    }
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(colors.background),
        ) {
            when {
                // ── Loading shimmer ───────────────────────────────────
                isLoading -> {
                    Column(
                        modifier            = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp),
                    ) {
                        repeat(4) { ShimmerAnnouncementCard() }
                    }
                }

                // ── Empty state ───────────────────────────────────────
                announcements.isEmpty() -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier            = Modifier.padding(32.dp),
                        ) {
                            Surface(
                                modifier = Modifier.size(100.dp),
                                shape    = CircleShape,
                                color    = colors.primaryContainer,
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        Icons.Outlined.Campaign,
                                        null,
                                        modifier = Modifier.size(50.dp),
                                        tint     = colors.primary.copy(alpha = 0.35f),
                                    )
                                }
                            }
                            Spacer(Modifier.height(24.dp))
                            Text(
                                "No announcements yet",
                                style      = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color      = colors.onSurface,
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                if (isAdmin) "Tap \"Post\" above to publish your first announcement."
                                else         "Nothing here yet. Check back later.",
                                style     = MaterialTheme.typography.bodyMedium,
                                color     = colors.onSurfaceVariant,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

                // ── List ──────────────────────────────────────────────
                else -> {
                    AnimatedVisibility(
                        visible = unreadCount > 0,
                        enter   = fadeIn() + expandVertically(),
                        exit    = fadeOut() + shrinkVertically(),
                    ) {
                        Surface(
                            color = colors.primaryContainer.copy(alpha = 0.6f),
                            shape = RoundedCornerShape(0.dp),
                        ) {
                            Row(
                                modifier          = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Surface(
                                    shape    = CircleShape,
                                    color    = colors.primary.copy(alpha = 0.15f),
                                    modifier = Modifier.size(32.dp),
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Outlined.Notifications, null, tint = colors.primary, modifier = Modifier.size(18.dp))
                                    }
                                }
                                Spacer(Modifier.width(12.dp))
                                Text(
                                    "You have $unreadCount unread announcement${if (unreadCount > 1) "s" else ""}",
                                    style      = MaterialTheme.typography.bodyMedium,
                                    color      = colors.primary,
                                    fontWeight = FontWeight.Medium,
                                )
                            }
                        }
                    }

                    LazyColumn(
                        modifier       = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(top = 10.dp, bottom = 32.dp),
                    ) {
                        items(announcements, key = { it.id }) { announcement ->
                            AnnouncementCard(
                                announcement = announcement,
                                onMarkAsRead = { viewModel.markAsRead(it) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ShimmerAnnouncementCard() {
    val colors = MaterialTheme.colorScheme
    val shimmerColors = listOf(
        colors.surfaceVariant,
        colors.surface,
        colors.surfaceVariant,
    )
    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue  = 0f,
        targetValue   = 1000f,
        animationSpec = infiniteRepeatable(tween(1200, easing = FastOutSlowInEasing), RepeatMode.Restart),
        label         = "shimmerX",
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 7.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.horizontalGradient(shimmerColors, translateAnim - 200f, translateAnim)),
    )
}