package com.nezuko.data.di

import com.nezuko.data.repository.AuthRepositoryImpl
import com.nezuko.data.repository.LocalCourseRepositoryImpl
import com.nezuko.data.repository.SearchCourseRepositoryImpl
import com.nezuko.data.repository.TokenRepositoryImpl
import com.nezuko.domain.repository.AuthRepository
import com.nezuko.domain.repository.LocalCourseRepository
import com.nezuko.domain.repository.SearchCourseRepository
import com.nezuko.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
    @Binds
    @Singleton
    fun authRepo(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun tokenRepo(impl: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    fun searchCourseRepo(impl: SearchCourseRepositoryImpl): SearchCourseRepository

    @Binds
    @Singleton
    fun localCourseRepo(impl: LocalCourseRepositoryImpl): LocalCourseRepository
}