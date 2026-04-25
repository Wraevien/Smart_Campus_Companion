package com.example.smartcampuscompanion.data

import com.google.firebase.Timestamp

data class Announcement(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val author: String = "",
    val date: String = "",
    val timestamp: Timestamp? = null,
    val readBy: List<String> = emptyList()
)

data class AnnouncementWithStatus(
    val id: String,
    val title: String,
    val content: String,
    val author: String,
    val date: String,
    val isRead: Boolean
)
