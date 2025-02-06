package com.nezuko.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.model.Course
import com.nezuko.domain.repository.LocalCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(
    private val localCourseRepository: LocalCourseRepository
) : ViewModel() {
    val likedCourses = localCourseRepository.courses

    fun like(course: Course) {
        viewModelScope.launch {
            localCourseRepository.likeCourse(course.copy(isFavourite = !course.isFavourite))
        }
    }
}