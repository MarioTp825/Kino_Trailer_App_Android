package com.tepe.domain.util.network

import com.tepe.domain.model.network.APIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> apiCall(call: suspend() -> T): APIResponse<T> {
    return try {
        val response = withContext(Dispatchers.IO) { call() }
        APIResponse.success(response)
    } catch (e: Exception) {
        APIResponse.error(e)
    }
}