package com.example.smartcampuscompanion.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AnnouncementDao {

    @Query("SELECT * FROM announcements ORDER BY id DESC")
    fun getAllAnnouncements(): Flow<List<Announcement>>

    @Insert
    suspend fun insertAnnouncement(announcement: Announcement)

    @Update
    suspend fun updateAnnouncement(announcement: Announcement)

    @Query("UPDATE announcements SET isRead = 1 WHERE id = :id")
    suspend fun markAsRead(id: Int)

    @Query("SELECT COUNT(*) FROM announcements WHERE isRead = 0")
    fun getUnreadCount(): Flow<Int>
}