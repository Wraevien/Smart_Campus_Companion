package com.example.smartcampuscompanion.data

import com.google.firebase.Timestamp

data class Task(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val dueAtMillis: Long = 0,
    val ownerUid: String = "",
    val timestamp: Timestamp? = null
)
