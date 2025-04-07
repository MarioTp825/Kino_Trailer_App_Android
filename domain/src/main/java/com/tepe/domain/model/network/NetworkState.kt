package com.tepe.domain.model.network

sealed class NetworkState private constructor(
    open val message: String? = null
) {

    data object None : NetworkState()
    data object Loading : NetworkState()
    data object Successful : NetworkState()
    data object Paginating : NetworkState()
    class Error(override val message: String) : NetworkState()

    fun isLoading(): Boolean {
        return this is Loading
    }
}