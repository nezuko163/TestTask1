package com.nezuko.domain.repository

interface NavigationRepository {
    var navigateFromHomeToCourse: (id: Long) -> Unit
    var navigateFromFavouriteToCourse: (id: Long) -> Unit
    var navigateFromAccountToCourse: (id: Long) -> Unit

    fun setUpNavigateFromHomeToCourse(nav: (id: Long) -> Unit)
    fun setUpNavigateFromFavouriteToCourse(nav: (id: Long) -> Unit)
    fun setUpNavigateFromAccountToCourse(nav: (id: Long) -> Unit)
}