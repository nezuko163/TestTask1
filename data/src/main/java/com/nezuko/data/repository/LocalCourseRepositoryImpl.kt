package com.nezuko.data.repository

import android.util.Log
import com.nezuko.data.sources.local.CourseDao
import com.nezuko.data.sources.local.toEntity
import com.nezuko.domain.model.Course
import com.nezuko.domain.repository.LocalCourseRepository
import io.ktor.util.Hash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class LocalCourseRepositoryImpl @Inject constructor(
    private val dao: CourseDao
) : LocalCourseRepository {
    private val TAG = "LocalCourseRepositoryImpl"
    override val courses: StateFlow<HashMap<Long, Course>> = dao.getAll()
        .map { list ->
            list
                .associate { Pair(it.id, it.toDTO()) }
                .let { HashMap(it) }
                .apply { _cache = this }
        }
        .stateIn(
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            started = SharingStarted.Lazily,
            initialValue = hashMapOf()
        )
    private var _cache = HashMap<Long, Course>()
    override val cache get() = _cache

    override suspend fun saveCourses(vararg courses: Course) {
        dao.insertCourses(*courses.map { it.toEntity() }.toTypedArray())
    }

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun likeCourse(course: Course) {
        if (!course.isFavourite) {
            dao.deleteById(course.id)
        } else {
            Log.i(
                TAG,
                "likeCourse: изменилось ${
                    dao.insertCourses(course.copy(isFavourite = true).toEntity())
                })"
            )
        }
    }

    override suspend fun likeCourse1(course: Course) {
        dao.updateCourses(course.toEntity())
    }
}