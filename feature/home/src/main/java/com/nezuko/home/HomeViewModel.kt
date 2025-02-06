package com.nezuko.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.nezuko.domain.model.Course
import com.nezuko.domain.model.CoursePlatform
import com.nezuko.domain.repository.LocalCourseRepository
import com.nezuko.domain.repository.SearchCourseRepository
import com.nezuko.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val searchCourseRepository: SearchCourseRepository,
    private val tokenRepository: TokenRepository,
    private val localCourseRepository: LocalCourseRepository
) : ViewModel() {
    private val TAG = "HomeViewModel"
    private val tokens = tokenRepository.tokens

    private val _query = MutableStateFlow("")
    val query get() = _query.value
    private val _platform = MutableStateFlow(CoursePlatform.STEPIK)

    fun like(course: Course) {
        viewModelScope.launch {
            Log.i(TAG, "like: course - ${course.isFavourite}")
            localCourseRepository.likeCourse(course)
            updateFavourite(course.id)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    var courses = _query
        .filter { it.isNotEmpty() }
        .flatMapLatest { query ->
            Log.i(TAG, "courses: $_query")
            searchCourseRepository.searchCourses(
                platform = _platform.value,
                token = tokens.value[_platform.value].orEmpty(),
                query = _query.value
            )
                .flowOn(Dispatchers.IO)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun setQuery(query: String) {
        _query.update { query }
    }

    private fun updateFavourite(courseId: Long) {
        courses = courses.map { pagingData ->
            pagingData.map { course ->
                course
            }
        }
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())
    }
}