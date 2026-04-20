package com.example.smartcampuscompanion.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.smartcampuscompanion.composables.AppTopBar
import com.example.smartcampuscompanion.data.Announcement
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.data.UserRole
import com.example.smartcampuscompanion.viewmodel.AnnouncementViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PostAnnouncementScreen(controller: NavController) {
    val context = LocalContext.current
    val session = remember { SessionManager(context) }
    val colors  = MaterialTheme.colorScheme

    if (session.getRole() != UserRole.ADMIN) {
        LaunchedEffect(Unit) { controller.popBackStack() }
        return
    }

    val viewModel: AnnouncementViewModel = viewModel()
    var title        by remember { mutableStateOf("") }
    var content      by remember { mutableStateOf("") }
    val author       = session.getUsername().replaceFirstChar { it.uppercase() }
    var titleError   by remember { mutableStateOf(false) }
    var contentError by remember { mutableStateOf(false) }
    var showSuccess  by remember { mutableStateOf(false) }

    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            kotlinx.coroutines.delay(1500)
            controller.popBackStack()
        }
    }

    Scaffold(containerColor = colors.background) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(pv),
        ) {
            // ── Consistent top bar ────────────────────────────────────
            AppTopBar(
                title    = "Post Announcement",
                subtitle = "Admin only",
                onBack   = { controller.popBackStack() },
            )

            // ── Form ──────────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                // Author chip (read-only)
                Surface(
                    shape = RoundedCornerShape(50),
                    color = colors.primaryContainer,
                ) {
                    Row(
                        modifier          = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Surface(
                            shape    = CircleShape,
                            color    = colors.primary.copy(alpha = 0.15f),
                            modifier = Modifier.size(28.dp),
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Outlined.AdminPanelSettings, null, tint = colors.primary, modifier = Modifier.size(16.dp))
                            }
                        }
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text("Posting as", style = MaterialTheme.typography.labelSmall, color = colors.onSurfaceVariant)
                            Text(author, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = colors.onPrimaryContainer)
                        }
                    }
                }

                // Title field
                Column {
                    Text("Title", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = colors.onBackground, modifier = Modifier.padding(bottom = 8.dp))
                    OutlinedTextField(
                        value          = title,
                        onValueChange  = { title = it; titleError = false },
                        placeholder    = { Text("e.g. Library Hours Update") },
                        isError        = titleError,
                        supportingText = if (titleError) ({ Text("Title cannot be empty") }) else null,
                        modifier       = Modifier.fillMaxWidth(),
                        shape          = RoundedCornerShape(16.dp),
                        colors         = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.primary,
                        ),
                        leadingIcon    = { Icon(Icons.Outlined.Title, null, tint = colors.primary.copy(0.7f)) },
                        singleLine     = true,
                    )
                }

                // Content field
                Column {
                    Text("Message", style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold, color = colors.onBackground, modifier = Modifier.padding(bottom = 8.dp))
                    OutlinedTextField(
                        value          = content,
                        onValueChange  = { content = it; contentError = false },
                        placeholder    = { Text("Write your announcement here…") },
                        isError        = contentError,
                        supportingText = if (contentError) ({ Text("Message cannot be empty") }) else null,
                        modifier       = Modifier.fillMaxWidth().heightIn(min = 160.dp),
                        shape          = RoundedCornerShape(16.dp),
                        colors         = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colors.primary,
                        ),
                        leadingIcon    = { Icon(Icons.Outlined.Edit, null, tint = colors.primary.copy(0.7f)) },
                        maxLines       = 10,
                    )
                    Text(
                        "${content.length} characters",
                        style    = MaterialTheme.typography.labelSmall,
                        color    = colors.onSurfaceVariant,
                        modifier = Modifier.align(Alignment.End).padding(top = 4.dp),
                    )
                }

                // Info card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = colors.secondaryContainer.copy(alpha = 0.5f),
                ) {
                    Row(
                        modifier          = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top,
                    ) {
                        Icon(Icons.Outlined.Info, null, tint = colors.secondary, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "This announcement will be visible to all students immediately upon posting.",
                            style      = MaterialTheme.typography.bodySmall,
                            color      = colors.onSurfaceVariant,
                            lineHeight = 18.sp,
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))

                // Submit button
                Button(
                    onClick = {
                        titleError   = title.isBlank()
                        contentError = content.isBlank()
                        if (!titleError && !contentError) {
                            val today = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(Date())
                            viewModel.addAnnouncement(
                                Announcement(
                                    title   = title.trim(),
                                    content = content.trim(),
                                    author  = author,
                                    date    = today,
                                )
                            )
                            showSuccess = true
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(54.dp),
                    shape    = RoundedCornerShape(16.dp),
                    colors   = ButtonDefaults.buttonColors(containerColor = colors.primary),
                    enabled  = !showSuccess,
                ) {
                    Icon(Icons.Outlined.Send, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("Publish Announcement", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                }

                // Success banner
                AnimatedVisibility(visible = showSuccess, enter = fadeIn(), exit = fadeOut()) {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = colors.secondaryContainer,
                    ) {
                        Row(
                            modifier          = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(Icons.Outlined.CheckCircle, null, tint = colors.secondary)
                            Spacer(Modifier.width(12.dp))
                            Text("Announcement published!", fontWeight = FontWeight.SemiBold, color = colors.secondary)
                        }
                    }
                }
            }
        }
    }
}