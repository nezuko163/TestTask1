package com.nezuko.domain.repository

import androidx.paging.PagingData
import com.nezuko.domain.model.CoursePlatform
import com.nezuko.domain.model.ResultModel
import com.nezuko.domain.model.Course
import kotlinx.coroutines.flow.Flow
import java.util.EnumMap

interface SearchCourseRepository {
    val cache: EnumMap<CoursePlatform, HashMap<Long, Course>>

    fun searchCourses(
        platform: CoursePlatform,
        token: String,
        query: String,
    ): Flow<PagingData<Course>>
}