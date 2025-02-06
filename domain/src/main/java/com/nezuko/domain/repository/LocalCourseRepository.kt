package com.nezuko.domain.repository

import com.nezuko.domain.model.Course
import kotlinx.coroutines.flow.StateFlow

interface LocalCourseRepository {
    val courses: StateFlow<HashMap<Long, Course>>
    val cache: HashMap<Long, Course>

    suspend fun saveCourses(vararg courses: Course)

    suspend fun deleteAll()

    suspend fun likeCourse(course: Course)

    suspend fun likeCourse1(course: Course)
}