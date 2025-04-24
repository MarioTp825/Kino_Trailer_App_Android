package com.tepe.data.database.config

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tepe.data.database.dao.MovieDao
import com.tepe.data.database.entities.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDataBase: RoomDatabase() {
    abstract fun movieDao(): MovieDao
}