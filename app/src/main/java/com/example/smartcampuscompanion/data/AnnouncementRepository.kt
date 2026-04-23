package com.example.smartcampuscompanion.data

import kotlinx.coroutines.flow.Flow

class AnnouncementRepository(private val dao: AnnouncementDao) {

    fun getAllAnnouncements(username: String): Flow<List<AnnouncementWithStatus>> = dao.getAllAnnouncements(username)
    fun getUnreadCount(username: String): Flow<Int> = dao.getUnreadCount(username)

    suspend fun insert(announcement: Announcement) {
        dao.insertAnnouncement(announcement)
    }

    suspend fun markAsRead(id: Int, username: String) {
        dao.markAsRead(AnnouncementReadStatus(id, username))
    }
}