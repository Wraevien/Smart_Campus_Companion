package com.example.smartcampuscompanion.data

import android.content.Context

enum class UserRole { ADMIN, STUDENT }

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)

    fun isLoggedIn(): Boolean = prefs.getBoolean(KEY_LOGGED_IN, false)
    fun getUsername(): String = prefs.getString(KEY_USERNAME, "") ?: ""
    fun getUid(): String = prefs.getString(KEY_UID, "") ?: ""

    fun getRole(): UserRole {
        val stored = prefs.getString(KEY_ROLE, UserRole.STUDENT.name) ?: UserRole.STUDENT.name
        return runCatching { UserRole.valueOf(stored) }.getOrDefault(UserRole.STUDENT)
    }

    fun isAdmin(): Boolean = getRole() == UserRole.ADMIN

    // ── Dark mode ─────────────────────────────────────────────────────
    fun isDarkMode(): Boolean = prefs.getBoolean(KEY_DARK_MODE, false)

    fun setDarkMode(enabled: Boolean) {
        prefs.edit().putBoolean(KEY_DARK_MODE, enabled).apply()
    }

    // ── Auth ──────────────────────────────────────────────────────────
    fun login(username: String, role: UserRole = UserRole.STUDENT, uid: String = "") {
        prefs.edit()
            .putBoolean(KEY_LOGGED_IN, true)
            .putString(KEY_USERNAME, username)
            .putString(KEY_ROLE, role.name)
            .putString(KEY_UID, uid)
            .apply()
    }

    fun logout() {
        // Keep dark-mode preference after logout
        val dark = isDarkMode()
        prefs.edit().clear().apply()
        prefs.edit().putBoolean(KEY_DARK_MODE, dark).apply()
    }

    companion object {
        private const val KEY_LOGGED_IN = "logged_in"
        private const val KEY_USERNAME  = "username"
        private const val KEY_ROLE      = "role"
        private const val KEY_DARK_MODE = "dark_mode"
        private const val KEY_UID       = "uid"
    }
}
