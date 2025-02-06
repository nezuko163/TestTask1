package com.nezuko.data.di

import com.nezuko.data.sources.remote.stepik.api.StepikApi
import com.nezuko.domain.repository.LocalCourseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Singleton
    @Provides
    fun providesStepikApi(httpClient: HttpClient, localCourseRepository: LocalCourseRepository) = StepikApi(httpClient, localCourseRepository)
}