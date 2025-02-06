package com.nezuko.auth

import androidx.lifecycle.ViewModel
import com.nezuko.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepository
) : ViewModel() {
    fun setAuth(auth: Boolean) {
        authRepo.setAuth(auth)
    }
}