package com.tepe.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieUI

@Entity
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    val id : String,
    val title: String,
    val youtubeId: String?,
    val imageUrl: String,
    val youtubeUrl: String,
    val websiteUrl: String?,
    val category: String,
    val views: Long = 0L,
) {

    fun toMovieUI(): MovieUI {
        return MovieUI(
            id = id,
            title = title,
            youtubeId = youtubeId,
            imageUrl = imageUrl,
            videoUrl = youtubeUrl,
            websiteUrl = websiteUrl,
            category = MovieGenre.fromUrlValue(category),
            views = views,
            favorite = true,
        )
    }

    companion object {
        fun fromMovieUI(movieUI: MovieUI): MovieEntity {
            return MovieEntity(
                id = movieUI.id,
                title = movieUI.title,
                youtubeId = movieUI.youtubeId,
                imageUrl = movieUI.imageUrl,
                youtubeUrl = movieUI.videoUrl,
                websiteUrl = movieUI.websiteUrl,
                category = movieUI.category.urlValue,
                views = movieUI.views,
            )
        }
    }
}