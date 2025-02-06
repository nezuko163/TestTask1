package com.nezuko.data.repository

import com.nezuko.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {
    private val _isAuth = MutableStateFlow(false)
    override val isAuth = _isAuth.asStateFlow()

    override fun setAuth(auth: Boolean) {
        if (auth != _isAuth.value) {
            _isAuth.update { auth }
        }
    }
}
