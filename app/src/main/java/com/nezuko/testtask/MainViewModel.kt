package com.nezuko.testtask

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.repository.AuthRepository
import com.nezuko.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val TAG = "MainViewModel"
    val user = authRepository.user

    fun loadTokens() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.i(TAG, "loadTokens: start")
            tokenRepository.loadTokens()
                .flowOn(Dispatchers.IO)
                .collect {
                    Log.i(TAG, "loadTokens: val - $it")
                }
            Log.i(TAG, "loadTokens: end")
        }
    }


    fun getCurrentUser() {
        viewModelScope.launch {
            authRepository.getCurrentUser()
        }
    }

    fun onCreate() {
        authRepository.onCreate()
    }

    fun onDestroy() {
        authRepository.onDestroy()
    }
}