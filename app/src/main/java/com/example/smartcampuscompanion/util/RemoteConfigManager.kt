package com.example.smartcampuscompanion.util

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

object RemoteConfigManager {
    private val remoteConfig: FirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()

    private const val TAG = "RemoteConfigManager"

    // Parameter Keys
    const val LOGIN_BANNER_TITLE = "login_banner_title"
    const val LOGIN_BANNER_MESSAGE = "login_banner_message"
    const val REGISTER_BANNER_TITLE = "register_banner_title"
    const val REGISTER_BANNER_MESSAGE = "register_banner_message"

    init {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(3600) // 1 hour
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)
        
        // Set default values
        val defaults = mapOf(
            LOGIN_BANNER_TITLE to "Login Successful",
            LOGIN_BANNER_MESSAGE to "Welcome back! Have a great day.",
            REGISTER_BANNER_TITLE to "Welcome aboard!",
            REGISTER_BANNER_MESSAGE to "Your account has been created."
        )
        remoteConfig.setDefaultsAsync(defaults)
    }

    fun fetchAndActivate(onComplete: (Boolean) -> Unit = {}) {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG, "Config params updated: $updated")
                } else {
                    Log.e(TAG, "Fetch failed")
                }
                onComplete(task.isSuccessful)
            }
    }

    fun getString(key: String): String {
        return remoteConfig.getString(key)
    }
}
