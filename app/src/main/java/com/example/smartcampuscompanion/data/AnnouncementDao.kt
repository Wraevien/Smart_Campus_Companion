package com.example.smartcampuscompanion.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnouncementDao {

    @Query("""
        SELECT a.*, EXISTS(SELECT 1 FROM announcement_read_status rs WHERE rs.announcementId = a.id AND rs.username = :username) as isRead 
        FROM announcements a ORDER BY a.id DESC
    """)
    fun getAllAnnouncements(username: String): Flow<List<AnnouncementWithStatus>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markAsRead(status: AnnouncementReadStatus)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnnouncement(announcement: Announcement)

    @Query("""
        SELECT COUNT(*) FROM announcements a 
        WHERE NOT EXISTS(SELECT 1 FROM announcement_read_status rs WHERE rs.announcementId = a.id AND rs.username = :username)
    """)
    fun getUnreadCount(username: String): Flow<Int>
}