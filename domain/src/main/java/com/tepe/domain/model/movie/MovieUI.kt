package com.tepe.domain.model.movie

import kotlinx.serialization.Serializable

@Serializable
data class MovieUI(
    val id: String,
    val title: String,
    val imageUrl: String,
    val videoUrl: String,
    val youtubeId: String? = null,
    val websiteUrl: String? = null,
    val category: MovieGenre,
    val views: Long = 0L,
    val favorite: Boolean = false,
)
