package com.tepe.mymovie.ui.navigation.model

import com.tepe.domain.model.movie.MovieUI
import kotlinx.serialization.Serializable

sealed class NavigationRoutes {


    @Serializable
    data object Home : NavigationRoutes()
    @Serializable
    data object Favorite : NavigationRoutes()
    @Serializable
    data object AppInfo : NavigationRoutes()

    @Serializable
    data class MovieDetail(
        val movie: MovieUI
    ) : NavigationRoutes()

    @Serializable
    data class FavoriteMovieInfo(
        val movie: MovieUI
    ): NavigationRoutes()

}