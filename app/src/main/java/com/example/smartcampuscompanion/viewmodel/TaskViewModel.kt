package com.example.smartcampuscompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcampuscompanion.data.db.TaskEntity
import com.example.smartcampuscompanion.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    private val _currentUser = MutableStateFlow("")
    
    fun setCurrentUser(username: String) {
        _currentUser.value = username
    }

    val tasks = _currentUser.flatMapLatest { username ->
        repository.observeTasks(username)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun upsertTask(
        id: Long = 0,
        title: String,
        description: String,
        dueAtMillis: Long
    ) {
        viewModelScope.launch {
            repository.upsert(
                TaskEntity(
                    id = id,
                    title = title,
                    description = description,
                    dueAtMillis = dueAtMillis,
                    ownerUsername = _currentUser.value
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
