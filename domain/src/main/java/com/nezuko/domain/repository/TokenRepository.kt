package com.nezuko.domain.repository

import com.nezuko.domain.model.CoursePlatform
import com.nezuko.domain.model.ResultModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TokenRepository {
    val tokens: StateFlow<Map<CoursePlatform, String>>

    suspend fun loadTokens(): Flow<ResultModel<Boolean>>
}