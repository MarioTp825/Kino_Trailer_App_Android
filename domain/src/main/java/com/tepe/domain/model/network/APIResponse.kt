package com.tepe.domain.model.network

class APIResponse<T> private constructor(
    val data: T? = null,
    val error: Exception? = null
) {

    val forceError get() = error ?: NullPointerException("Data is null")

    companion object {
        fun <T> success(data: T): APIResponse<T> {
            return APIResponse(data = data)
        }

        fun <T> error(error: Exception): APIResponse<T> {
            return APIResponse(error = error)
        }
    }
}
