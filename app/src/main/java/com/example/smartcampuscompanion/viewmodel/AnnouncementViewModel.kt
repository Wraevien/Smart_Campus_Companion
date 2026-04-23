package com.example.smartcampuscompanion.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcampuscompanion.data.Announcement
import com.example.smartcampuscompanion.data.AnnouncementDatabase
import com.example.smartcampuscompanion.data.AnnouncementRepository
import com.example.smartcampuscompanion.data.AnnouncementWithStatus
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class AnnouncementViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AnnouncementRepository
    private val _currentUser = MutableStateFlow("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun setCurrentUser(username: String) {
        _currentUser.value = username
    }

    val announcements: StateFlow<List<AnnouncementWithStatus>>
    val unreadCount: StateFlow<Int>

    init {
        val dao = AnnouncementDatabase.getDatabase(application).announcementDao()
        repository = AnnouncementRepository(dao)

        announcements = _currentUser.flatMapLatest { username ->
            repository.getAllAnnouncements(username)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        unreadCount = _currentUser.flatMapLatest { username ->
            repository.getUnreadCount(username)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )
    }

    fun markAsRead(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                repository.markAsRead(id, _currentUser.value)
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