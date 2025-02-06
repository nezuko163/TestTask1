package com.nezuko.data.sources.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nezuko.data.sources.remote.stepik.api.StepikApi
import com.nezuko.domain.model.Course
import com.nezuko.domain.model.CoursePlatform
import com.nezuko.domain.model.ResultModel
import javax.inject.Inject

class CoursePagingSource @Inject constructor(
    private val stepikApi: StepikApi,
    private val query: String,
    private val platform: CoursePlatform,
    private val token: String,
) : PagingSource<Int, Course>() {
    private val TAG = "CoursePagingSource"

    override fun getRefreshKey(state: PagingState<Int, Course>): Int? {
        return state.anchorPosition
    }

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Course> {
        return try {
            val page = params.key ?: 1  // Текущая страница (если null, начинаем с 1)
            val countTake = params.loadSize
            Log.i(TAG, "load: cpunt - $countTake")
            when (platform) {
                CoursePlatform.STEPIK -> {
                    val response = stepikApi.fetchCourses1(
                        token = token,
                        page = page,
                        countTake = countTake,
                        query = query
                    )

                    if (response.status == ResultModel.Status.SUCCESS) {
                        val data = response.data!!
                        Log.i(TAG, "load: page - $page")
                        Log.i(TAG, "load: size - ${data.size}")
                        Log.i(TAG, "load: load - ${params.loadSize}")
                        LoadResult.Page(
                            data = data,
                            prevKey = null,
                            nextKey = if (response.data!!.isEmpty()) null else if (response.data!!.size < countTake) page + 1 else page
                        )
                    } else {
                        Log.e(TAG, "load: ${response.message}")
                        LoadResult.Error(RuntimeException(response.message))
                    }
                }

                else -> LoadResult.Error(RuntimeException("Нет допустимой платформы"))
            }


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}