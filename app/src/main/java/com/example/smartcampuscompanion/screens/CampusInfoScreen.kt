package com.example.smartcampuscompanion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.smartcampuscompanion.composables.AppTopBar
import com.example.smartcampuscompanion.composables.DepartmentCard
import com.example.smartcampuscompanion.data.DepartmentRepository

@Composable
fun CampusInfoScreen(controller: NavController) {
    val departments = DepartmentRepository.getDepartments()
    val colors      = MaterialTheme.colorScheme

    var searchQuery by remember { mutableStateOf("") }

    val filtered = remember(departments, searchQuery) {
        if (searchQuery.isBlank()) departments
        else departments.filter { it.name.contains(searchQuery, ignoreCase = true) }
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
                title    = "Campus Information",
                subtitle = "${departments.size} departments",
                onBack   = { controller.popBackStack() },
            )

            // ── Search bar ────────────────────────────────────────────
            OutlinedTextField(
                value         = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder   = { Text("Search departments…") },
                leadingIcon   = { Icon(Icons.Outlined.Search, null, tint = colors.onSurfaceVariant) },
                trailingIcon  = if (searchQuery.isNotBlank()) ({
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Outlined.Clear, "Clear")
                    }
                }) else null,
                singleLine = true,
                shape      = RoundedCornerShape(16.dp),
                modifier   = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor      = colors.primary,
                    unfocusedBorderColor    = colors.outline,
                    focusedContainerColor   = colors.surface,
                    unfocusedContainerColor = colors.surface,
                ),
            )

            // ── Info banner ───────────────────────────────────────────
            if (searchQuery.isBlank()) {
                Surface(
                    color    = colors.primaryContainer.copy(alpha = 0.5f),
                    shape    = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 8.dp),
                ) {
                    Row(
                        modifier          = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            Icons.Outlined.Lightbulb,
                            null,
                            tint     = colors.primary,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            "Tap a card to expand contact details.",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.onSurfaceVariant,
                        )
                    }
                }
            }

            // ── Content ───────────────────────────────────────────────
            if (filtered.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier            = Modifier.padding(32.dp),
                    ) {
                        Surface(modifier = Modifier.size(88.dp), shape = CircleShape, color = colors.primaryContainer) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(Icons.Outlined.SearchOff, null, modifier = Modifier.size(44.dp), tint = colors.primary.copy(alpha = 0.5f))
                            }
                        }
                        Spacer(Modifier.height(20.dp))
                        Text(
                            "No results for \"$searchQuery\"",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color      = colors.onBackground,
                            textAlign  = TextAlign.Center,
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "Try a different search term.",
                            style     = MaterialTheme.typography.bodyMedium,
                            color     = colors.onSurfaceVariant,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier       = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 24.dp),
                ) {
                    itemsIndexed(filtered) { idx, department ->
                        // Pass index so each card gets a distinct accent color
                        DepartmentCard(department = department, index = idx)
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}