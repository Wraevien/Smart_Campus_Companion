package com.example.smartcampuscompanion.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.smartcampuscompanion.data.Department

// Cycle through distinct accent colors for each card
private val accentPalette = listOf(
    Color(0xFF015DB6), // brand blue
    Color(0xFF059669), // emerald
    Color(0xFF7C3AED), // violet
    Color(0xFFD97706), // amber
    Color(0xFFDC2626), // rose
    Color(0xFF0891B2), // cyan
)

@Composable
fun DepartmentCard(department: Department, index: Int = 0) {
    val accent = accentPalette[index % accentPalette.size]
    val colors = MaterialTheme.colorScheme

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape     = RoundedCornerShape(20.dp),
        colors    = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        onClick   = { expanded = !expanded },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
        ) {
            // Colored left accent strip
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(5.dp)
                    .background(
                        Brush.verticalGradient(listOf(accent, accent.copy(alpha = 0.4f))),
                        RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp),
                    )
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 18.dp, vertical = 16.dp),
            ) {
                // Header row: icon + name + chevron
                Row(
                    modifier          = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Icon circle
                    Surface(
                        shape    = RoundedCornerShape(12.dp),
                        color    = accent.copy(alpha = 0.12f),
                        modifier = Modifier.size(44.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.Apartment,
                                contentDescription = null,
                                tint     = accent,
                                modifier = Modifier.size(24.dp),
                            )
                        }
                    }

                    Spacer(Modifier.width(14.dp))

                    Text(
                        text       = department.name,
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color      = colors.onSurface,
                        modifier   = Modifier.weight(1f),
                    )

                    Icon(
                        imageVector = if (expanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                        contentDescription = if (expanded) "Collapse" else "Expand",
                        tint     = colors.onSurfaceVariant,
                        modifier = Modifier.size(22.dp),
                    )
                }

                // Always visible: building chip
                Spacer(Modifier.height(12.dp))
                InfoChip(
                    icon  = Icons.Outlined.LocationOn,
                    label = department.building,
                    color = accent,
                )

                // Expanded details
                if (expanded) {
                    Spacer(Modifier.height(10.dp))
                    HorizontalDivider(color = colors.outlineVariant.copy(alpha = 0.5f))
                    Spacer(Modifier.height(10.dp))

                    InfoChip(
                        icon  = Icons.Outlined.Phone,
                        label = department.contactNumber,
                        color = colors.secondary,
                    )
                    Spacer(Modifier.height(8.dp))
                    InfoChip(
                        icon  = Icons.Outlined.Email,
                        label = department.email,
                        color = colors.primary,
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text  = department.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.onSurfaceVariant,
                    )
                }
            }
        }
    }
}

@Composable
private fun InfoChip(icon: ImageVector, label: String, color: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(
            shape    = CircleShape,
            color    = color.copy(alpha = 0.12f),
            modifier = Modifier.size(28.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = color, modifier = Modifier.size(15.dp))
            }
        }
        Spacer(Modifier.width(10.dp))
        Text(
            text  = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = FontWeight.Medium,
        )
    }
}