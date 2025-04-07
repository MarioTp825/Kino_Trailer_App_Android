package com.tepe.domain.dashboard

import com.tepe.domain.model.movie.MovieGroup
import com.tepe.domain.model.config.DashboardSchema
import com.tepe.domain.model.movie.MovieGenre
import kotlinx.coroutines.flow.MutableStateFlow

typealias MutableMovieState = Collection<MutableStateFlow<MovieGroup>>

class DashboardPageManager {

    private var dashboardSchema: DashboardSchema? = null

    private var pages: MutableMap<MovieGenre, MutableStateFlow<MovieGroup>> =
        mutableMapOf()

    val dashboardPages: MutableMovieState
        get() = pages.minus(MovieGenre.Main).values

    val mainGenre: MutableStateFlow<MovieGroup>? get() = pages[MovieGenre.Main]

    val schemaGenre get() = dashboardSchema?.schema?.map { it.genre }.orEmpty()


    fun setDashboardSchema(dashboardSchema: DashboardSchema) {
        this.dashboardSchema = dashboardSchema
        setUpDashboardPages()
    }

    suspend fun setError(genre: MovieGenre, error: String?) {
        val page = pages[genre] ?: return

        pages[genre]?.emit(
            page.value.copy(
                isLoading = false,
                error = error ?: "Unknown error occurred"
            )
        )
    }

    suspend fun fetchTrailersPage(group: MovieGroup) {
        val category = group.movieGenre
        val page = pages[category] ?: return

        pages[category]?.emit(
            group.copy(
                movies = page.value.movies + group.movies,
                isLoading = false
            )
        )
        fetchMainDisplayPage(pages[category]?.value)
    }

    private suspend fun fetchMainDisplayPage(page: MovieGroup?) {
        page ?: return
        val mainGroup = pages[MovieGenre.Main]?.value ?: return
        val featuredMovie = page.movies.maxByOrNull { it.views } ?: return

        val doesFeatureMovieExist = mainGroup.movies.any { it.id == featuredMovie.id }
        val doesGenreExist = mainGroup.movies.any { it.category == featuredMovie.category }
        if (doesFeatureMovieExist || doesGenreExist) return

        pages[MovieGenre.Main]?.emit(
            mainGroup.copy(
                movies = mainGroup.movies + featuredMovie,
            )
        )
    }

    private fun setUpDashboardPages() = dashboardSchema?.schema?.let { schema ->
        val pageSchema = schema.associate {
            it.genre to MutableStateFlow(
                MovieGroup(
                    title = it.title,
                    movies = emptyList(),
                    movieGenre = it.genre,
                    isLoading = true
                )
            )
        }
        val mainMovies = MovieGenre.Main to MutableStateFlow(
            MovieGroup(
                title = "Main",
                movies = emptyList(),
                movieGenre = MovieGenre.Main,
                isLoading = true
            ),
        )
        pages = (pageSchema + mainMovies).toMutableMap()
    }


    operator fun get(genre: MovieGenre) = pages[genre]
}