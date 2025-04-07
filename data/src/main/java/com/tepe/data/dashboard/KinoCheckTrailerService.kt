package com.tepe.data.dashboard

import com.tepe.data.dashboard.responses.KinoTrailerResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface KinoCheckTrailerService {

    @GET("trailers/latest")
    suspend fun getTrailerByCategory(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("genres") category: String,
        @Query("language") lang: String = "en",
    ): KinoTrailerResponse
}