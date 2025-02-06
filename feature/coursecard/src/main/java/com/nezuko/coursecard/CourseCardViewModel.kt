package com.nezuko.coursecard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.model.Course
import com.nezuko.domain.model.CoursePlatform
import com.nezuko.domain.repository.LocalCourseRepository
import com.nezuko.domain.repository.SearchCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourseCardViewModel @Inject constructor(
    private val localCourseRepository: LocalCourseRepository,
    private val searchCourseRepository: SearchCourseRepository
) : ViewModel() {

    val cache = searchCourseRepository.cache.apply {
        this[CoursePlatform.STEPIK] = localCourseRepository.cache
    }
    fun likeCourse(course: Course) {
        viewModelScope.launch {
            localCourseRepository.likeCourse(course)
        }
    }
}