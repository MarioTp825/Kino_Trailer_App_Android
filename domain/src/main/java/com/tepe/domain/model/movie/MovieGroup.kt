package com.tepe.domain.model.movie

data class MovieGroup(
    val title: String,
    val movies: List<MovieUI>,
    val movieGenre: MovieGenre,
    val currentPage: Int = 0,
    val totalPage: Int = Int.MAX_VALUE,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
    val hasMoreContent
        get() = currentPage < totalPage
}