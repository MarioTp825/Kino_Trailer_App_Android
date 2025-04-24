package com.tepe.mymovie.config.screenRouters

import com.tepe.cross_platform_integration.model.ScreenRouter
import com.tepe.domain.repository.MovieLocalRepository
import com.tepe.mymovie.utils.DataBaseScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailScreenRouter @Inject constructor(
    private val repository: MovieLocalRepository
) : ScreenRouter() {
    companion object {
        private const val STORE_MOVIE = "storeMovieDetail"
        private const val DELETE_MOVIE = "deleteMovieDetail"
    }

    override val route: String = "/movie/detail"
    override val sendMethod: String = "setMovieDetail"
    override val callMethod: List<String> = listOf(STORE_MOVIE, DELETE_MOVIE)

    override fun receivedMessage(method: String, message: String?) {
        DataBaseScope.launch {
            when (method) {
                STORE_MOVIE -> repository.storeMovie(message)
                DELETE_MOVIE -> repository.deleteMovie(message)
            }
        }
    }
}

class MovieInfoScreenRouter @Inject constructor(
    private val repository: MovieLocalRepository
) : ScreenRouter() {
    companion object {
        private const val STORE_MOVIE = "storeMovieDetail"
        private const val DELETE_MOVIE = "deleteMovieDetail"
    }

    override val route: String = "temp/movie/info"
    override val sendMethod: String = "setMovieInfo"
    override val callMethod: List<String> = listOf(STORE_MOVIE, DELETE_MOVIE)

    override fun receivedMessage(method: String, message: String?) {
        DataBaseScope.launch {
            when(method) {
                STORE_MOVIE -> repository.storeMovie(message)
                DELETE_MOVIE -> repository.deleteMovie(message)
            }
        }
    }
}