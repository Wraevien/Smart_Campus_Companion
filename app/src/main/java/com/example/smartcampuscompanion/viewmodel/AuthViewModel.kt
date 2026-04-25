package com.example.smartcampuscompanion.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcampuscompanion.data.SessionManager
import com.example.smartcampuscompanion.data.UserRole
import com.example.smartcampuscompanion.data.repository.AuthRepository
import com.example.smartcampuscompanion.data.AppUser
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository(),
    private val sessionManager: SessionManager
) : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Idle)
    val authState = _authState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid
                if (uid != null) {
                    val userData = repository.getUserData(uid)
                    if (userData != null) {
                        sessionManager.login(userData.username, userData.role, uid)
                        _authState.value = AuthState.Success(userData)
                    } else {
                        _authState.value = AuthState.Error("User data not found")
                    }
                } else {
                    _authState.value = AuthState.Error("Login failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun register(username: String, email: String, password: String, role: UserRole = UserRole.STUDENT) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val uid = result.user?.uid
                if (uid != null) {
                    val newUser = AppUser(uid = uid, username = username, email = email, role = role)
                    repository.saveUserData(newUser)
                    sessionManager.login(username, role, uid)
                    _authState.value = AuthState.Success(newUser)
                } else {
                    _authState.value = AuthState.Error("Registration failed")
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun logout() {
        repository.logout()
        sessionManager.logout()
    }
}

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class Success(val user: AppUser) : AuthState()
    data class Error(val message: String) : AuthState()
}
