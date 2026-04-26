package com.example.smartcampuscompanion.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.smartcampuscompanion.R
import com.example.smartcampuscompanion.data.SessionManager

class NotificationHelper(private val context: Context) {

    private val sessionManager = SessionManager(context)

    companion object {
        const val CHANNEL_ANNOUNCEMENTS = "announcements_channel_v2"
        const val CHANNEL_REMINDERS = "reminders_channel_v2"
        const val NOTIFICATION_ID_ANNOUNCEMENT = 101
        const val NOTIFICATION_ID_REMINDER = 102
    }

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val announcementChannel = NotificationChannel(
                CHANNEL_ANNOUNCEMENTS,
                "Campus Announcements",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Urgent notifications for campus updates"
                enableLights(true)
                enableVibration(true)
            }

            val reminderChannel = NotificationChannel(
                CHANNEL_REMINDERS,
                "Task Reminders",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "High priority reminders for your tasks"
                enableLights(true)
                enableVibration(true)
            }

            val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(announcementChannel)
            manager.createNotificationChannel(reminderChannel)
        }
    }

    fun showAnnouncementNotification(title: String, message: String, id: Int = NOTIFICATION_ID_ANNOUNCEMENT) {
        val enabled = sessionManager.areNotificationsEnabled()
        Log.d("NotificationHelper", "showAnnouncementNotification called. Title: $title, Enabled: $enabled")
        
        if (!enabled) return

        val builder = NotificationCompat.Builder(context, CHANNEL_ANNOUNCEMENTS)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .setAutoCancel(true)

        try {
            Log.d("NotificationHelper", "Showing notification: $title - $message")
            with(NotificationManagerCompat.from(context)) {
                notify(id, builder.build())
            }
        } catch (e: SecurityException) {
            Log.e("NotificationHelper", "Permission error: ${e.message}")
        }
    }

    fun showReminderNotification(title: String, message: String) {
        if (!sessionManager.areNotificationsEnabled()) return

        val builder = NotificationCompat.Builder(context, CHANNEL_REMINDERS)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setAutoCancel(true)

        try {
            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID_REMINDER, builder.build())
            }
        } catch (e: SecurityException) {
            // Permission not granted
        }
    }
}
