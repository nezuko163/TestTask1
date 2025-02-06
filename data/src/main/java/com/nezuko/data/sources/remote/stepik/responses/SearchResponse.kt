package com.nezuko.data.sources.remote.stepik.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Meta(
    val page: Int,
    @SerialName("has_next") val hasNext: Boolean,
    @SerialName("has_previous") val hasPrevious: Boolean,
)

@Serializable
data class SearchCourseResponse(
    val id: Long,
    val course: Long,
)

@Serializable
data class SearchResponse(
    val meta: Meta,
    @SerialName("search-results") val searchResults: List<SearchCourseResponse>
)