package com.nezuko.testtask.navigation

import com.nezuko.data.repository.AuthRepositoryImpl
import com.nezuko.domain.repository.AuthRepository
import com.nezuko.domain.repository.NavigationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NavigationModule {
    @Binds
    @Singleton
    fun navRepo(impl: NavigationImpl): NavigationRepository
}