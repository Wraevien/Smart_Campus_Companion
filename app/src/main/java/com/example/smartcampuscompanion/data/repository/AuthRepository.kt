package com.example.smartcampuscompanion.data.repository

import com.example.smartcampuscompanion.data.UserRole
import com.example.smartcampuscompanion.data.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    fun getCurrentUserUid(): String? = auth.currentUser?.uid

    suspend fun getUserData(uid: String): AppUser? {
        return try {
            val snapshot = usersCollection.document(uid).get().await()
            snapshot.toObject(AppUser::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun saveUserData(user: AppUser) {
        usersCollection.document(user.uid).set(user).await()
    }

    fun logout() {
        auth.signOut()
    }
}
