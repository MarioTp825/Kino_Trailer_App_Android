package com.tepe.domain.repository

import com.tepe.domain.model.config.DashboardSchema
import com.tepe.domain.model.movie.MovieGenre
import com.tepe.domain.model.movie.MovieUI
import com.tepe.domain.model.network.PageResponse

interface DashboardRepository {

    suspend fun getTrailers(category: MovieGenre, page: Int): PageResponse<MovieUI>

    suspend fun getSchema(): DashboardSchema
}