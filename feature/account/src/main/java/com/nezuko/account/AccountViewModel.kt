package com.nezuko.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nezuko.domain.model.Course
import com.nezuko.domain.repository.AuthRepository
import com.nezuko.domain.repository.LocalCourseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val localCourseRepository: LocalCourseRepository
) : ViewModel() {
    fun signOut() {
        authRepository.signOut()
    }

    val likedCourses = localCourseRepository.courses

    fun like(course: Course) {
        viewModelScope.launch {
            localCourseRepository.likeCourse(course.copy(isFavourite = !course.isFavourite))
        }
    }
}