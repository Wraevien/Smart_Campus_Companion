package com.example.smartcampuscompanion.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartcampuscompanion.ui.theme.BluePrimary

// One consistent gradient used across ALL screen top bars
val AppBarGradient: Brush
    @Composable get() = Brush.linearGradient(
        listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary.copy(alpha = 0.80f),
        )
    )

/**
 * Consistent top app bar used on every screen except the Dashboard.
 *
 * @param title       Main title text
 * @param subtitle    Optional smaller text below the title
 * @param onBack      If non-null, shows a back arrow button
 * @param actions     Slot for end-side icon buttons (e.g. "Post" button for admin)
 */
@Composable
fun AppTopBar(
    title    : String,
    subtitle : String?   = null,
    onBack   : (() -> Unit)? = null,
    actions  : @Composable RowScope.() -> Unit = {},
) {
    val gradient = AppBarGradient

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(gradient)
            .statusBarsPadding()
            .padding(horizontal = 8.dp, vertical = 12.dp),
    ) {
        // Back button (start)
        if (onBack != null) {
            IconButton(
                onClick  = onBack,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.18f)),
            ) {
                Icon(
                    imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint               = Color.White,
                )
            }
        }

        // Centre: title + optional subtitle
        Column(
            modifier            = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text       = title,
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color      = Color.White,
            )
            if (subtitle != null) {
                Text(
                    text  = subtitle,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.White.copy(alpha = 0.75f),
                )
            }
        }

        // End actions
        Row(
            modifier          = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            content           = actions,
        )
    }
}