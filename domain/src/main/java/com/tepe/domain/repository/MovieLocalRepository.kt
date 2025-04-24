package com.tepe.domain.repository

import com.tepe.domain.model.movie.MovieUI
import kotlinx.coroutines.flow.Flow

interface MovieLocalRepository {

    suspend fun getStoredMovies(): Flow<List<MovieUI>>

    suspend fun storeMovie(movie: String?)

    suspend fun getMovieById(id: String): MovieUI?

    suspend fun deleteMovie(movie: String?)
}