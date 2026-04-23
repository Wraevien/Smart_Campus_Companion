package com.example.smartcampuscompanion.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "announcements")
data class Announcement(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val content: String,
    val author: String,
    val date: String
)

@Entity(tableName = "announcement_read_status", primaryKeys = ["announcementId", "username"])
data class AnnouncementReadStatus(
    val announcementId: Int,
    val username: String,
    val isRead: Boolean = true
)

data class AnnouncementWithStatus(
    val id: Int,
    val title: String,
    val content: String,
    val author: String,
    val date: String,
    val isRead: Boolean
)