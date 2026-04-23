package com.example.smartcampuscompanion.data.repository

import com.example.smartcampuscompanion.data.db.TaskDao
import com.example.smartcampuscompanion.data.db.TaskEntity
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val dao: TaskDao) {

    fun observeTasks(username: String): Flow<List<TaskEntity>> = dao.observeTasks(username)

    suspend fun upsert(task: TaskEntity) {
        dao.upsert(task)
    }

    suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}
