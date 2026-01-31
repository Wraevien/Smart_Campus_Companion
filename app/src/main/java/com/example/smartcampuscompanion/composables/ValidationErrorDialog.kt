package com.example.smartcampuscompanion.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ValidationErrorDialog(onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text("OK")
            }
        },
        title = { Text("Missing Info") },
        text = { Text("Please fill in both the username and password fields.") }
    )
}