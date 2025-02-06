package com.nezuko.domain.model

import java.time.LocalDate

data class Course(
    val id: Long,
    val score: Double,
    val title: String,
    val platform: CoursePlatform,
    val cover: String,
    val priceDisplay: String,
    val description: String,
    var isFavourite: Boolean,
    val authorCover: String,
    val author: String,
    val urlCourse: String,
    val dateCreated: LocalDate,
)
