package com.tepe.data.dashboard.responses

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class Metadata(
    @SerializedName("limit")
    val limit: Int? = null,
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null
)

@Keep
@JsonClass(generateAdapter = true)
data class Movies(
    @SerializedName("categories")
    val categories: List<String?>? = null,
    @SerializedName("genres")
    val genres: List<String?>? = null,
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("published")
    val published: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("views")
    val views: Long? = null,
    @SerializedName("youtube_channel_id")
    val youtubeChannelId: String? = null,
    @SerializedName("youtube_thumbnail")
    val youtubeThumbnail: String? = null,
    @SerializedName("youtube_video_id")
    val youtubeVideoId: String? = null
)

@Keep
@JsonClass(generateAdapter = true)
data class KinoTrailerResponse(
    val movies: Map<String, Movies>? = null,
    @SerializedName("_metadata")
    val metadata: Metadata? = null
)


