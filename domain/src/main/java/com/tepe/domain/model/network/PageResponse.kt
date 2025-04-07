package com.tepe.domain.model.network

data class PageResponse<T>(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val result: List<T>
)