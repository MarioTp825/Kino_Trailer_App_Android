package com.tepe.domain.model.movie

import java.io.Serializable

data class MovieUI(
    val id: String,
    val title: String,
    val imageUrl: String,
    val videoUrl: String,
    val category: MovieGenre,
    val views: Long = 0L,
): Serializable
