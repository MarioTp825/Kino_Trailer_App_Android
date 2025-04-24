package com.tepe.data.database.config

import android.content.Context
import androidx.room.Room
import com.tepe.data.database.dao.MovieDao

class DataBaseAccess(context: Context) {
    private val db: MovieDataBase by lazy {
        Room.databaseBuilder(
            context,
            MovieDataBase::class.java, "movie_database"
        ).build()
    }

    fun getMovieDao(): MovieDao {
        return db.movieDao()
    }
}