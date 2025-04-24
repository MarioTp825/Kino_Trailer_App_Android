package com.tepe.mymovie.config.di.entryPoints

import com.tepe.mymovie.config.screenRouters.AppInfoScreenRouter
import com.tepe.mymovie.config.screenRouters.MovieDetailScreenRouter
import com.tepe.mymovie.config.screenRouters.MovieInfoScreenRouter
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ScreenRouteEntryPoint {
    fun getMovieDetailScreen(): MovieDetailScreenRouter
    fun getMovieInfoScreen(): MovieInfoScreenRouter
    fun getAppInfoScreen(): AppInfoScreenRouter
}