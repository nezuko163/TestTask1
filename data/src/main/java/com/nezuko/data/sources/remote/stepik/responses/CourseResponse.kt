package com.nezuko.data.sources.remote.stepik.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val id: Long,
    val summary: String,
    val cover: String,
    val description: String,
    val authors: List<Long>,
    val tags: List<Int>,
    @SerialName("review_summary") val reviewSummary: Long,
    @SerialName("display_price") val displayPrice: String,
    val difficulty: String?,
    @SerialName("canonical_url") val canonicalUrl: String,
    val title: String,
    val slug: String,
    @SerialName("create_date") val createDate: String
)

@Serializable
data class CoursesResponse(
    val meta: Meta,
    val courses: List<Course>
)