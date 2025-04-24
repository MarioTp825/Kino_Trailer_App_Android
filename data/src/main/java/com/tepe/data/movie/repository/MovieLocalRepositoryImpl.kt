package com.tepe.data.movie.repository

import com.google.gson.Gson
import com.tepe.data.database.dao.MovieDao
import com.tepe.data.database.entities.MovieEntity
import com.tepe.domain.model.movie.MovieUI
import com.tepe.domain.repository.MovieLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieLocalRepositoryImpl(
    private val movieDao: MovieDao
) : MovieLocalRepository {
    override suspend fun getStoredMovies(): Flow<List<MovieUI>> {
        return movieDao.getMovies().map { movieEntities ->
            movieEntities.map { movieEntity ->
                movieEntity.toMovieUI()
            }
        }
    }

    override suspend fun storeMovie(movie: String?) {
        movie ?: return
        val movieUI = Gson().fromJson(movie, MovieUI::class.java)
        val entity = movieDao.getMovieById(movieUI.id)
        if (entity != null) {
            movieDao.insertMovie(entity)
            return
        }
        val data = MovieEntity.fromMovieUI(movieUI)
        movieDao.insertMovie(data)
    }

    override suspend fun getMovieById(id: String): MovieUI? {
        return movieDao.getMovieById(id)?.toMovieUI()
    }

    override suspend fun deleteMovie(movie: String?) {
        movie ?: return
        val movieUI = Gson().fromJson(movie, MovieUI::class.java)
        val movieEntity = movieDao.getMovieById(movieUI.id) ?: return
        movieDao.deleteMovie(movieEntity)
    }
}
