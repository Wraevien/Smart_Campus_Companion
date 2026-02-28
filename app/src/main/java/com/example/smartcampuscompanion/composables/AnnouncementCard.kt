package com.example.smartcampuscompanion.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.smartcampuscompanion.data.Announcement

@Composable
fun AnnouncementCard(
    announcement: Announcement,
    onMarkAsRead: (Int) -> Unit
) {
    val cardColor = if (announcement.isRead) Color(0xFFF5F5F5) else Color.White
    val borderColor = if (announcement.isRead) Color.LightGray else Color(0xFF599E29)

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (announcement.isRead) 1.dp else 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = announcement.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (announcement.isRead) Color.Gray else Color(0xFF015DB6),
                    modifier = Modifier.weight(1f)
                )
                if (!announcement.isRead) {
                    Text(
                        text = "NEW",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .then(
                                Modifier
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = announcement.author,
                    fontSize = 12.sp,
                    color = Color(0xFF599E29),
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = announcement.date,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = announcement.content,
                fontSize = 13.sp,
                color = if (announcement.isRead) Color.Gray else Color.DarkGray,
                lineHeight = 19.sp
            )

            if (!announcement.isRead) {
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { onMarkAsRead(announcement.id) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF599E29),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Mark as Read", fontSize = 12.sp)
                }
            }
        }
    }
}