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
import com.example.smartcampuscompanion.data.Announcement
import com.example.smartcampuscompanion.ui.theme.BluePrimary
import com.example.smartcampuscompanion.ui.theme.GreenPrimary

@Composable
fun AnnouncementCard(
    announcement: Announcement,
    onMarkAsRead: (Int) -> Unit
) {
    val isUnread = !announcement.isRead
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .shadow(
                elevation = if (isUnread) 12.dp else 4.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color.Black.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // Subtle Blue indicator for unread
            if (isUnread) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(6.dp)
                        .background(BluePrimary)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(24.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = announcement.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333), // Changed to Dark Gray (Requested)
                        modifier = Modifier.weight(1f),
                        letterSpacing = (-0.3).sp
                    )
                    
                    if (isUnread) {
                        Surface(
                            color = BluePrimary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = "NEW",
                                color = BluePrimary,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                letterSpacing = 1.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    InfoLabel(
                        icon = Icons.Default.Person,
                        text = announcement.author,
                        color = GreenPrimary
                    )
                    InfoLabel(
                        icon = Icons.Default.DateRange,
                        text = announcement.date,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = announcement.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (isUnread) Color(0xFF4A4A4A) else Color.Gray,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight.Normal
                )

                if (isUnread) {
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    Surface(
                        onClick = { onMarkAsRead(announcement.id) },
                        color = BluePrimary,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .align(Alignment.End)
                            .shadow(4.dp, RoundedCornerShape(16.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Mark as read",
                                color = Color.White,
                                fontWeight = FontWeight.Medium,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoLabel(
    icon: ImageVector,
    text: String,
    color: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            color = color.copy(alpha = 0.1f),
            shape = CircleShape,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.padding(6.dp),
                tint = color
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}
