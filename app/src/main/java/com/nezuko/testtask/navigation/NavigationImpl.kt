package com.nezuko.testtask.navigation

import com.nezuko.domain.repository.NavigationRepository
import javax.inject.Inject

class NavigationImpl @Inject constructor() : NavigationRepository {
    override var navigateFromHomeToCourse = { id: Long -> }
    override var navigateFromFavouriteToCourse = { id: Long -> }
    override var navigateFromAccountToCourse = { id: Long -> }

    override fun setUpNavigateFromHomeToCourse(nav: (id: Long) -> Unit) {
        navigateFromHomeToCourse = nav
    }

    override fun setUpNavigateFromFavouriteToCourse(nav: (id: Long) -> Unit) {
        navigateFromFavouriteToCourse = nav
    }

    override fun setUpNavigateFromAccountToCourse(nav: (id: Long) -> Unit) {
        navigateFromAccountToCourse = nav
    }
}