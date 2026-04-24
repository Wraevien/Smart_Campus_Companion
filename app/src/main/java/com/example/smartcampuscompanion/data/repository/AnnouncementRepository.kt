package com.example.smartcampuscompanion.data.repository

import android.util.Log
import com.example.smartcampuscompanion.data.Announcement
import com.example.smartcampuscompanion.data.AnnouncementWithStatus
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class AnnouncementRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val announcementsCollection = firestore.collection("announcements")

    fun getAllAnnouncements(username: String): Flow<List<AnnouncementWithStatus>> = callbackFlow {
        val subscription = announcementsCollection
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("AnnouncementRepo", "Firestore error: ${error.message}")
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val list = snapshot?.documents?.mapNotNull { doc ->
                    val announcement = doc.toObject(Announcement::class.java)
                    announcement?.let {
                        AnnouncementWithStatus(
                            id = it.id.ifEmpty { doc.id },
                            title = it.title,
                            content = it.content,
                            author = it.author,
                            date = it.date,
                            isRead = it.readBy.contains(username)
                        )
                    }
                } ?: emptyList()
                trySend(list)
            }
        awaitClose { subscription.remove() }
    }

    fun getUnreadCount(username: String): Flow<Int> = callbackFlow {
        val subscription = announcementsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                trySend(0)
                return@addSnapshotListener
            }
            val docs = snapshot?.documents ?: emptyList()
            val count = docs.count { doc ->
                val readBy = doc.get("readBy") as? List<*>
                val hasRead = readBy?.contains(username) ?: false
                !hasRead
            }
            trySend(count)
        }
        awaitClose { subscription.remove() }
    }

    suspend fun insert(announcement: Announcement) {
        try {
            val doc = announcementsCollection.document()
            val data = announcement.copy(
                id = doc.id, 
                timestamp = com.google.firebase.Timestamp.now(),
                readBy = emptyList()
            )
            doc.set(data).await()
        } catch (e: Exception) {
            Log.e("AnnouncementRepo", "Insert error: ${e.message}")
        }
    }

    suspend fun markAsRead(id: String, username: String) {
        try {
            announcementsCollection.document(id)
                .update("readBy", FieldValue.arrayUnion(username))
                .await()
        } catch (e: Exception) {
            Log.e("AnnouncementRepo", "MarkRead error: ${e.message}")
        }
    }

    suspend fun deleteAnnouncement(id: String) {
        try {
            announcementsCollection.document(id).delete().await()
        } catch (e: Exception) {
            Log.e("AnnouncementRepo", "Delete error: ${e.message}")
        }
    }
}
