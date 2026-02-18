package com.example.smartcampuscompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcampuscompanion.data.db.TaskEntity
import com.example.smartcampuscompanion.data.repository.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(
    private val repository: TaskRepository
) : ViewModel() {

    val tasks = repository.tasks
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    fun addTask(
        title: String,
        description: String,
        dueAtMillis: Long
    ) {
        viewModelScope.launch {
            repository.upsert(
                TaskEntity(
                    title = title,
                    description = description,
                    dueAtMillis = dueAtMillis
                )
            )
        }
    }

    fun deleteTask(id: Long) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}
