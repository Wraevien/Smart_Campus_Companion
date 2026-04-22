package com.example.smartcampuscompanion.util

import com.example.smartcampuscompanion.data.UserRole

fun UserRole.isAdmin(): Boolean = this == UserRole.ADMIN
fun UserRole.isStudent(): Boolean = this == UserRole.STUDENT
