package com.example.smartcampuscompanion.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcampuscompanion.data.AnnouncementWithStatus

@Composable
fun AnnouncementCard(
    announcement : AnnouncementWithStatus,
    onMarkAsRead : (Int) -> Unit,
) {
    val isUnread = !announcement.isRead
    val colors   = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(
                elevation  = if (isUnread) 8.dp else 2.dp,
                shape      = RoundedCornerShape(24.dp),
                spotColor  = colors.primary.copy(alpha = 0.15f),
            ),
        shape  = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            // Accent strip for unread
            if (isUnread) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(5.dp)
                        .background(
                            colors.primary,
                            RoundedCornerShape(topStart = 24.dp, bottomStart = 24.dp),
                        )
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(
                        start  = if (isUnread) 18.dp else 22.dp,
                        end    = 22.dp,
                        top    = 20.dp,
                        bottom = 20.dp,
                    ),
            ) {
                // Title row
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                ) {
                    Text(
                        text       = announcement.title,
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color      = colors.onSurface,
                        modifier   = Modifier.weight(1f),
                    )

                    if (isUnread) {
                        Surface(
                            color    = colors.primary.copy(alpha = 0.10f),
                            shape    = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(start = 12.dp),
                        ) {
                            Text(
                                text       = "NEW",
                                color      = colors.primary,
                                fontSize   = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier   = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                letterSpacing = 1.sp,
                            )
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                // Meta: author + date
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    AnnouncementInfoLabel(
                        icon  = Icons.Default.Person,
                        text  = announcement.author,
                        color = colors.secondary,
                    )
                    AnnouncementInfoLabel(
                        icon  = Icons.Default.DateRange,
                        text  = announcement.date,
                        color = colors.onSurfaceVariant,
                    )
                }

                Spacer(Modifier.height(12.dp))

                // Body
                Text(
                    text       = announcement.content,
                    style      = MaterialTheme.typography.bodyMedium,
                    color      = if (isUnread) colors.onSurface else colors.onSurfaceVariant,
                    lineHeight = 22.sp,
                )

                // Mark as read button
                if (isUnread) {
                    Spacer(Modifier.height(18.dp))
                    Surface(
                        onClick  = { onMarkAsRead(announcement.id) },
                        color    = colors.primary,
                        shape    = RoundedCornerShape(14.dp),
                        modifier = Modifier.align(Alignment.End),
                    ) {
                        Row(
                            modifier          = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                null,
                                modifier = Modifier.size(15.dp),
                                tint     = colors.onPrimary,
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                "Mark as read",
                                color      = colors.onPrimary,
                                fontWeight = FontWeight.Medium,
                                fontSize   = 13.sp,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnnouncementInfoLabel(icon: ImageVector, text: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            color    = color.copy(alpha = 0.10f),
            shape    = CircleShape,
            modifier = Modifier.size(24.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, modifier = Modifier.size(14.dp), tint = color)
            }
        }
        Spacer(Modifier.width(6.dp))
        Text(
            text       = text,
            fontSize   = 12.sp,
            color      = color,
            fontWeight = FontWeight.Medium,
        )
    }
}