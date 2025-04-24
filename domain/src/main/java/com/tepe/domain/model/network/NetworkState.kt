package com.tepe.domain.model.network

sealed class NetworkState private constructor(
    open val message: String? = null
) {

    data object None : NetworkState()
    data object Loading : NetworkState()
    data object Successful : NetworkState()
    data object Paginating : NetworkState()
    class Error(msg: String? = null) : NetworkState() {
        override val message: String = msg ?: "Unknown Error"
    }

    fun isLoading(): Boolean {
        return this is Loading
    }
}