package com.example.smartcampuscompanion.data

import kotlinx.coroutines.flow.Flow

class AnnouncementRepository(private val dao: AnnouncementDao) {

    val allAnnouncements: Flow<List<Announcement>> = dao.getAllAnnouncements()
    val unreadCount: Flow<Int> = dao.getUnreadCount()

    suspend fun insert(announcement: Announcement) {
        dao.insertAnnouncement(announcement)
    }

    suspend fun markAsRead(id: Int) {
        dao.markAsRead(id)
    }

    suspend fun update(announcement: Announcement) {
        dao.updateAnnouncement(announcement)
    }
}