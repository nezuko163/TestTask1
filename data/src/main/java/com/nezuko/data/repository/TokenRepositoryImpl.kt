package com.nezuko.data.repository

import android.util.Log
import com.nezuko.data.sources.remote.stepik.api.StepikApi
import com.nezuko.domain.model.CoursePlatform
import com.nezuko.domain.model.ResultModel
import com.nezuko.domain.repository.TokenRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import java.util.EnumMap
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val stepikApi: StepikApi
) : TokenRepository {
    private val TAG = "TokenRepositoryImpl"
    private val _tokens =
        MutableStateFlow<MutableMap<CoursePlatform, String>>(EnumMap(CoursePlatform::class.java))
    override val tokens = _tokens.asStateFlow()

    override suspend fun loadTokens() = flow {
        emit(ResultModel.loading())
        Log.i(TAG, "loadTokens: start2")
        val stepikToken = stepikApi.getToken()
        Log.i(TAG, "loadTokens: $stepikToken")
        _tokens.update { map ->
            map.also {
                if (stepikToken.status == ResultModel.Status.SUCCESS && !stepikToken.data.isNullOrEmpty()) {
                    map[CoursePlatform.STEPIK] = stepikToken.data!!
                }
            }
        }
        emit(ResultModel.success(true))
    }

}
