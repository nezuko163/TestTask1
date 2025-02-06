package com.nezuko.data.sources.remote.stepik.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserResponse(
    val meta: Meta,
    val users: List<User>
)

@Serializable
data class User(
    val id: Long,
    val profile: Long,
    @SerialName("full_name") val fullName: String,
    val alias: String? = null,
    val avatar: String? = null,
)