package com.example.smartcampuscompanion.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Announcement::class], version = 1, exportSchema = false)
abstract class AnnouncementDatabase : RoomDatabase() {

    abstract fun announcementDao(): AnnouncementDao

    companion object {
        @Volatile
        private var INSTANCE: AnnouncementDatabase? = null

        fun getDatabase(context: Context): AnnouncementDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnnouncementDatabase::class.java,
                    "announcement_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            // Pre-populate with sample announcements
                            INSTANCE?.let { database ->
                                CoroutineScope(Dispatchers.IO).launch {
                                    database.announcementDao().insertAnnouncement(
                                        Announcement(
                                            title = "Enrollment for 2nd Semester Now Open",
                                            content = "The Registrar's Office announces that enrollment for the Second Semester of AY 2025-2026 is now open. Please visit the Registrar's Office or the student portal to complete your enrollment. Deadline is February 28, 2026.",
                                            author = "Registrar's Office",
                                            date = "February 20, 2026"
                                        )
                                    )
                                    database.announcementDao().insertAnnouncement(
                                        Announcement(
                                            title = "Campus Wi-Fi Maintenance Schedule",
                                            content = "The IT Department will be conducting scheduled maintenance on the campus Wi-Fi network on February 25, 2026 from 10:00 PM to 2:00 AM. Expect intermittent connectivity during this period.",
                                            author = "IT Department",
                                            date = "February 19, 2026"
                                        )
                                    )
                                    database.announcementDao().insertAnnouncement(
                                        Announcement(
                                            title = "Intramural Sports Week — Registration Open",
                                            content = "The Student Affairs Office invites all students to participate in the upcoming Intramural Sports Week scheduled on March 10–14, 2026. Register your team at the SAO office by March 1, 2026.",
                                            author = "Student Affairs Office",
                                            date = "February 18, 2026"
                                        )
                                    )
                                    database.announcementDao().insertAnnouncement(
                                        Announcement(
                                            title = "Library Extended Hours During Finals",
                                            content = "The University Library will extend its operating hours to 7:00 AM – 9:00 PM starting March 1 through March 15, 2026 to support students during the final examination period.",
                                            author = "Library Services",
                                            date = "February 17, 2026"
                                        )
                                    )
                                    database.announcementDao().insertAnnouncement(
                                        Announcement(
                                            title = "Scholarship Applications for AY 2026-2027",
                                            content = "The Office of Student Affairs is now accepting scholarship applications for Academic Year 2026-2027. Eligible students must have a GWA of 1.75 or better and must not have any failing grades. Submit requirements to the SAO office before March 5, 2026.",
                                            author = "Student Affairs Office",
                                            date = "February 15, 2026"
                                        )
                                    )
                                }
                            }
                        }
                    })
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}