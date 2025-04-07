package com.tepe.domain.model.config

import com.tepe.domain.model.movie.MovieGenre

data class DashboardSchema(
    val title: String,
    val schema: List<MovieSection>
) {
    data class MovieSection(
        val title: String,
        val genre: MovieGenre
    )
}
