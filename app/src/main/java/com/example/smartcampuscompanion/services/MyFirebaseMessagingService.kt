package com.example.smartcampuscompanion.services

import android.util.Log
import com.example.smartcampuscompanion.util.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("FCM", "From: ${remoteMessage.from}")

        val notificationHelper = NotificationHelper(applicationContext)
        val id = System.currentTimeMillis().toInt()

        // Use the new v2 channel for FCM messages to ensure banner shows
        if (remoteMessage.notification != null) {
            val title = remoteMessage.notification?.title ?: "Smart Campus"
            val body = remoteMessage.notification?.body ?: ""
            notificationHelper.showAnnouncementNotification(title, body, id)
        } 
        else if (remoteMessage.data.isNotEmpty()) {
            val title = remoteMessage.data["title"] ?: "Smart Campus"
            val body = remoteMessage.data["body"] ?: ""
            notificationHelper.showAnnouncementNotification(title, body, id)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FCM", "Refreshed token: $token")
        // Normally you would send this token to your backend server
    }
}
