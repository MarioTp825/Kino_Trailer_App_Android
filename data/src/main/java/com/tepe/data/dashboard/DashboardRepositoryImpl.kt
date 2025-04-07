package com.tepe.data.dashboard

import com.tepe.data.utils.TrailerPageLimit
import com.tepe.data.utils.YoutubeUrl
import com.tepe.domain.model.config.DashboardSchema
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieUI
import com.tepe.domain.model.network.PageResponse
import com.tepe.domain.repository.DashboardRepository
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class DashboardRepositoryImpl(
    private val api: KinoCheckTrailerService
): DashboardRepository {

    override suspend fun getTrailers(category: MovieGenre, page: Int): PageResponse<MovieUI> {
        return api.getTrailerByCategory(page = page, limit = TrailerPageLimit, category = category.urlValue).let {
            PageResponse(
                page = it.metadata?.page ?: 0,
                totalPages = it.metadata?.totalPages ?: 0,
                totalResults = it.metadata?.totalCount ?: 0,
                result = it.movies?.values?.map { trailer ->
                    MovieUI(
                        id = trailer.id.orEmpty(),
                        title = trailer.title.orEmpty(),
                        imageUrl = trailer.thumbnail.orEmpty(),
                        videoUrl = YoutubeUrl + trailer.youtubeVideoId,
                        category = category,
                        views = trailer.views ?: 0L
                    )
                } ?: emptyList()
            )
        }
    }

    override suspend fun getSchema(): DashboardSchema {
        delay(1.seconds)
        return DashboardSchema(
            title = "Dashboard",
            schema = MovieGenre.entries.map { genre ->
                DashboardSchema.MovieSection(
                    title = genre.name,
                    genre = genre
                )
            }
        )
    }
}