package com.example.smartcampuscompanion.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun LoginErrorDialog(onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            androidx.compose.material3.TextButton(onClick = onDismiss) {
                Text("Try Again")
            }
        },
        title = { Text("Login Failed") },
        text = { Text("The username or password you entered is incorrect.") }
    )
}