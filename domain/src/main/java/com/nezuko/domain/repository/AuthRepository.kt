package com.nezuko.domain.repository

import com.nezuko.domain.model.ResultModel
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val user: StateFlow<ResultModel<String?>>

    suspend fun getCurrentUser(): String?
    suspend fun signInWithEmailAndPassword(email: String, password: String): String?
    suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
    ): String
    fun signOut()

    fun onCreate()
    fun onDestroy()
}