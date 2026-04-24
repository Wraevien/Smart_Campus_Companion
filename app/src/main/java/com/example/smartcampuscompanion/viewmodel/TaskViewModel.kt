package com.example.smartcampuscompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcampuscompanion.data.Task
import com.example.smartcampuscompanion.data.repository.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository = TaskRepository()) : ViewModel() {

    private val _currentUserUid = MutableStateFlow("")
    
    fun setCurrentUser(uid: String) {
        _currentUserUid.value = uid
    }

    val tasks = _currentUserUid.flatMapLatest { uid ->
        repository.observeTasks(uid)
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun upsertTask(
        id: String = "",
        title: String,
        description: String,
        dueAtMillis: Long
    ) {
        viewModelScope.launch {
            repository.upsert(
                Task(
                    id = id,
                    title = title,
                    description = description,
                    dueAtMillis = dueAtMillis,
                    ownerUid = _currentUserUid.value
                )
            )
        }
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            repository.deleteById(id)
        }
    }
}
