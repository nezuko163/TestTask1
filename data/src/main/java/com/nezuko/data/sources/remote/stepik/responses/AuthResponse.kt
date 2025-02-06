package com.nezuko.data.sources.remote.stepik.responses

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)