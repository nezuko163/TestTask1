package com.nezuko.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nezuko.data.sources.paging.CoursePagingSource
import com.nezuko.data.sources.remote.stepik.api.StepikApi
import com.nezuko.domain.model.Course
import com.nezuko.domain.model.CoursePlatform
import com.nezuko.domain.repository.LocalCourseRepository
import com.nezuko.domain.repository.SearchCourseRepository
import kotlinx.coroutines.flow.Flow
import java.util.EnumMap
import javax.inject.Inject

class SearchCourseRepositoryImpl @Inject constructor(
    private val stepikApi: StepikApi,
) : SearchCourseRepository {
    private val stepikCache
        get() = stepikApi.cache

    private val _cache: EnumMap<CoursePlatform, HashMap<Long, Course>> = EnumMap(CoursePlatform::class.java)
    override val cache: EnumMap<CoursePlatform, HashMap<Long, Course>>
        get() {
            _cache[CoursePlatform.STEPIK] = stepikCache
            return _cache
        }

    override fun searchCourses(
        platform: CoursePlatform,
        token: String,
        query: String,
    ): Flow<PagingData<Course>> {
        return Pager(
            config = PagingConfig(
                pageSize = 3,
                enablePlaceholders = false,
                prefetchDistance = 5,
                initialLoadSize = 10
            ),
            pagingSourceFactory = {
                CoursePagingSource(
                    stepikApi = stepikApi,
                    query = query,
                    platform = CoursePlatform.STEPIK,
                    token = token
                )
            }
        ).flow
    }
}