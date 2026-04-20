package com.example.smartcampuscompanion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcampuscompanion.data.Announcement
import com.example.smartcampuscompanion.data.AnnouncementDatabase
import com.example.smartcampuscompanion.data.AnnouncementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnnouncementViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AnnouncementRepository

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    val announcements: StateFlow<List<Announcement>>
    val unreadCount: StateFlow<Int>

    init {
        val dao = AnnouncementDatabase.getDatabase(application).announcementDao()
        repository = AnnouncementRepository(dao)

        announcements = repository.allAnnouncements.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        unreadCount = repository.unreadCount.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    }

    fun markAsRead(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.markAsRead(id)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun addAnnouncement(announcement: Announcement) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.insert(announcement)
            } finally {
                _isLoading.value = false
            }
        }
    }
}