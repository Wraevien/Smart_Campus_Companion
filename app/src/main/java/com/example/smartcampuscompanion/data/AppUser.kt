package com.example.smartcampuscompanion.data

data class AppUser(
    val uid: String = "",
    val username: String = "",
    val email: String = "",
    val role: UserRole = UserRole.STUDENT
)
