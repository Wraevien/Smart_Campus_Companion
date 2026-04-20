package com.example.smartcampuscompanion.screens

import android.content.Context
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.smartcampuscompanion.composables.AppTopBar
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.data.UserRole
import com.example.smartcampuscompanion.ui.theme.LocalDarkTheme

private const val PREFS_PROFILE = "profile_prefs"
private const val KEY_FULL_NAME = "full_name"
private const val KEY_EMAIL     = "email"
private const val KEY_PHONE     = "phone"
private const val KEY_NOTIF     = "notifications_enabled"

@Composable
fun ProfileScreen(controller: NavController) {

    val context  = LocalContext.current
    val session  = remember { SessionManager(context) }
    val prefs    = remember { context.getSharedPreferences(PREFS_PROFILE, Context.MODE_PRIVATE) }
    val colors   = MaterialTheme.colorScheme

    val username = session.getUsername()
    val isAdmin  = session.getRole() == UserRole.ADMIN

    // ── Editable fields ───────────────────────────────────────────────
    var fullName by remember { mutableStateOf(prefs.getString(KEY_FULL_NAME, "") ?: "") }
    var email    by remember { mutableStateOf(prefs.getString(KEY_EMAIL,     "") ?: "") }
    var phone    by remember { mutableStateOf(prefs.getString(KEY_PHONE,     "") ?: "") }

    // ── Toggles ───────────────────────────────────────────────────────
    var notificationsOn by remember { mutableStateOf(prefs.getBoolean(KEY_NOTIF, true)) }

    // Dark mode wired to the global CompositionLocal so it actually changes the theme
    val darkState  = LocalDarkTheme.current
    val isDark     by darkState

    // ── Edit mode ─────────────────────────────────────────────────────
    var isEditing   by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    var draftName  by remember { mutableStateOf(fullName) }
    var draftEmail by remember { mutableStateOf(email) }
    var draftPhone by remember { mutableStateOf(phone) }

    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            kotlinx.coroutines.delay(2000)
            showSuccess = false
        }
    }

    Scaffold(containerColor = colors.background) { pv ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colors.background)
                .padding(pv)
                .verticalScroll(rememberScrollState()),
        ) {

            // ── Consistent top bar with Edit/Save action ───────────────
            AppTopBar(
                title    = "My Profile",
                onBack   = { controller.popBackStack() },
                actions  = {
                    Surface(
                        onClick  = {
                            if (isEditing) {
                                fullName = draftName.trim()
                                email    = draftEmail.trim()
                                phone    = draftPhone.trim()
                                prefs.edit()
                                    .putString(KEY_FULL_NAME, fullName)
                                    .putString(KEY_EMAIL,     email)
                                    .putString(KEY_PHONE,     phone)
                                    .apply()
                                showSuccess = true
                            } else {
                                draftName  = fullName
                                draftEmail = email
                                draftPhone = phone
                            }
                            isEditing = !isEditing
                        },
                        shape = RoundedCornerShape(50),
                        color = Color.White.copy(alpha = 0.22f),
                    ) {
                        Row(
                            modifier          = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = if (isEditing) Icons.Outlined.Check else Icons.Outlined.Edit,
                                contentDescription = null,
                                tint     = Color.White,
                                modifier = Modifier.size(16.dp),
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                text       = if (isEditing) "Save" else "Edit",
                                color      = Color.White,
                                style      = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                    Spacer(Modifier.width(6.dp))
                },
            )

            // ── Avatar block ──────────────────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.primary.copy(alpha = 0.08f)),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    modifier            = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Surface(
                        shape    = CircleShape,
                        color    = colors.primary,
                        modifier = Modifier.size(90.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text       = username.take(1).uppercase(),
                                fontSize   = 36.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color      = colors.onPrimary,
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text       = username.replaceFirstChar { it.uppercase() },
                        style      = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.ExtraBold,
                        color      = colors.onBackground,
                    )
                    Spacer(Modifier.height(6.dp))
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = colors.primaryContainer,
                    ) {
                        Row(
                            modifier          = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                if (isAdmin) Icons.Outlined.AdminPanelSettings else Icons.Outlined.School,
                                null,
                                tint     = colors.primary,
                                modifier = Modifier.size(14.dp),
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                if (isAdmin) "Administrator" else "Student",
                                style      = MaterialTheme.typography.labelMedium,
                                color      = colors.primary,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }
            }

            // ── Success banner ────────────────────────────────────────
            AnimatedVisibility(visible = showSuccess, enter = fadeIn(), exit = fadeOut()) {
                Surface(color = colors.secondaryContainer, modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier          = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(Icons.Outlined.CheckCircle, null, tint = colors.secondary, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(10.dp))
                        Text("Profile saved successfully!", fontWeight = FontWeight.SemiBold, color = colors.secondary)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // ── Personal Information ───────────────────────────────────
            ProfileSectionLabel("Personal Information", Icons.Outlined.Person)
            Spacer(Modifier.height(12.dp))

            ProfileField(
                label         = "Full Name",
                value         = if (isEditing) draftName  else fullName,
                placeholder   = "Enter your full name",
                icon          = Icons.Outlined.Badge,
                isEditing     = isEditing,
                onValueChange = { draftName = it },
            )
            Spacer(Modifier.height(12.dp))
            ProfileField(
                label         = "Username",
                value         = username,
                placeholder   = "",
                icon          = Icons.Outlined.AccountCircle,
                isEditing     = false,
                onValueChange = {},
            )
            Spacer(Modifier.height(12.dp))
            ProfileField(
                label         = "Email",
                value         = if (isEditing) draftEmail else email,
                placeholder   = "Enter your email address",
                icon          = Icons.Outlined.Email,
                isEditing     = isEditing,
                onValueChange = { draftEmail = it },
            )
            Spacer(Modifier.height(12.dp))
            ProfileField(
                label         = "Phone",
                value         = if (isEditing) draftPhone else phone,
                placeholder   = "Enter your phone number",
                icon          = Icons.Outlined.Phone,
                isEditing     = isEditing,
                onValueChange = { draftPhone = it },
            )

            Spacer(Modifier.height(28.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = colors.outlineVariant)
            Spacer(Modifier.height(28.dp))

            // ── Preferences ───────────────────────────────────────────
            ProfileSectionLabel("Preferences", Icons.Outlined.Settings)
            Spacer(Modifier.height(12.dp))

            PreferenceToggle(
                title           = "Push Notifications",
                subtitle        = "Receive alerts for new announcements",
                icon            = Icons.Outlined.Notifications,
                iconColor       = colors.primary,
                checked         = notificationsOn,
                onCheckedChange = {
                    notificationsOn = it
                    prefs.edit().putBoolean(KEY_NOTIF, it).apply()
                },
            )

            Spacer(Modifier.height(12.dp))

            // Dark mode — actually toggles the theme via LocalDarkTheme
            PreferenceToggle(
                title           = "Dark Mode",
                subtitle        = if (isDark) "Dark appearance is on" else "Light appearance is on",
                icon            = if (isDark) Icons.Outlined.DarkMode else Icons.Outlined.LightMode,
                iconColor       = Color(0xFF7C3AED),
                checked         = isDark,
                onCheckedChange = { enabled ->
                    darkState.value = enabled
                    session.setDarkMode(enabled)   // persists across restarts
                },
            )

            Spacer(Modifier.height(28.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 20.dp), color = colors.outlineVariant)
            Spacer(Modifier.height(28.dp))

            // ── Account ───────────────────────────────────────────────
            ProfileSectionLabel("Account", Icons.Outlined.ManageAccounts)
            Spacer(Modifier.height(12.dp))

            Surface(
                shape    = RoundedCornerShape(16.dp),
                color    = colors.surfaceVariant,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            ) {
                Row(
                    modifier          = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Surface(
                        shape    = CircleShape,
                        color    = colors.primary.copy(alpha = 0.12f),
                        modifier = Modifier.size(40.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                if (isAdmin) Icons.Outlined.AdminPanelSettings else Icons.Outlined.School,
                                null,
                                tint     = colors.primary,
                                modifier = Modifier.size(22.dp),
                            )
                        }
                    }
                    Spacer(Modifier.width(14.dp))
                    Column {
                        Text("Account Role", style = MaterialTheme.typography.labelSmall, color = colors.onSurfaceVariant)
                        Text(
                            if (isAdmin) "Administrator" else "Student",
                            style      = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color      = colors.onSurface,
                        )
                    }
                }
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

// ── Sub-composables ───────────────────────────────────────────────────────────

@Composable
private fun ProfileSectionLabel(label: String, icon: ImageVector) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier          = Modifier.padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(icon, null, tint = colors.primary, modifier = Modifier.size(18.dp))
        Spacer(Modifier.width(8.dp))
        Text(label, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold, color = colors.onBackground)
    }
}

@Composable
private fun ProfileField(
    label         : String,
    value         : String,
    placeholder   : String,
    icon          : ImageVector,
    isEditing     : Boolean,
    onValueChange : (String) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    if (isEditing) {
        OutlinedTextField(
            value         = value,
            onValueChange = onValueChange,
            label         = { Text(label) },
            placeholder   = { Text(placeholder, color = colors.outline) },
            leadingIcon   = { Icon(icon, null, tint = colors.primary.copy(alpha = 0.7f)) },
            modifier      = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            shape         = RoundedCornerShape(16.dp),
            singleLine    = true,
            colors        = OutlinedTextFieldDefaults.colors(
                focusedBorderColor   = colors.primary,
                unfocusedBorderColor = colors.outline,
            ),
        )
    } else {
        Surface(
            shape    = RoundedCornerShape(16.dp),
            color    = colors.surfaceVariant,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
        ) {
            Row(
                modifier          = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Surface(
                    shape    = CircleShape,
                    color    = colors.primary.copy(alpha = 0.10f),
                    modifier = Modifier.size(36.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(icon, null, tint = colors.primary, modifier = Modifier.size(18.dp))
                    }
                }
                Spacer(Modifier.width(14.dp))
                Column {
                    Text(label, style = MaterialTheme.typography.labelSmall, color = colors.onSurfaceVariant)
                    Text(
                        text       = value.ifBlank { "Not set" },
                        style      = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color      = if (value.isBlank()) colors.outline else colors.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
private fun PreferenceToggle(
    title           : String,
    subtitle        : String,
    icon            : ImageVector,
    iconColor       : Color,
    checked         : Boolean,
    onCheckedChange : (Boolean) -> Unit,
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        shape    = RoundedCornerShape(16.dp),
        color    = colors.surfaceVariant,
        modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
    ) {
        Row(
            modifier          = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                shape    = CircleShape,
                color    = iconColor.copy(alpha = 0.12f),
                modifier = Modifier.size(40.dp),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = iconColor, modifier = Modifier.size(22.dp))
                }
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title,    style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold, color = colors.onSurface)
                Text(subtitle, style = MaterialTheme.typography.labelSmall, color = colors.onSurfaceVariant)
            }
            Switch(
                checked         = checked,
                onCheckedChange = onCheckedChange,
                colors          = SwitchDefaults.colors(
                    checkedThumbColor   = Color.White,
                    checkedTrackColor   = iconColor,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = colors.outline.copy(alpha = 0.4f),
                ),
            )
        }
    }
}