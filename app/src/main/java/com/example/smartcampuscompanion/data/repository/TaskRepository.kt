package com.example.smartcampuscompanion.data.repository

import android.util.Log
import com.example.smartcampuscompanion.data.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class TaskRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val tasksCollection = firestore.collection("tasks")

    fun observeTasks(ownerUid: String): Flow<List<Task>> = callbackFlow {
        if (ownerUid.isEmpty()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        // We remove the .orderBy("dueAtMillis") to avoid requiring a manual index in Firebase Console
        // We will sort the results in memory instead.
        val subscription = tasksCollection
            .whereEqualTo("ownerUid", ownerUid)
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    Log.e("TaskRepository", "Firestore error: ${error.message}")
                    trySend(emptyList())
                    return@addSnapshotListener
                }
                val tasks = snapshot?.toObjects(Task::class.java) ?: emptyList()
                val sortedTasks = tasks.sortedBy { it.dueAtMillis }
                trySend(sortedTasks)
            }
        awaitClose { subscription.remove() }
    }

    suspend fun upsert(task: Task) {
        try {
            val doc = if (task.id.isEmpty()) tasksCollection.document() else tasksCollection.document(task.id)
            val data = task.copy(id = doc.id, timestamp = com.google.firebase.Timestamp.now())
            doc.set(data).await()
        } catch (e: Exception) {
            Log.e("TaskRepository", "Upsert error: ${e.message}")
        }
    }

    suspend fun deleteById(id: String) {
        try {
            tasksCollection.document(id).delete().await()
        } catch (e: Exception) {
            Log.e("TaskRepository", "Delete error: ${e.message}")
        }
    }
}
