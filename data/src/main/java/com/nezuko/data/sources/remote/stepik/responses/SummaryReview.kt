package com.nezuko.data.sources.remote.stepik.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CourseReviewSummaryResponse(
    val meta: Meta,
    @SerialName("course-review-summaries") val courseReviewSummaries: List<CourseReviewSummary>
)

@Serializable
data class CourseReviewSummary(
    val id: Int,
    val course: Int,
    val average: Double,
    val count: Int,
    val distribution: List<Int>
)