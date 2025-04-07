package com.tepe.data.network

import retrofit2.HttpException

fun getNetworkMessageError(e: Exception): String {
    return when(e) {
        is java.net.UnknownHostException -> "No internet connection"
        is java.net.SocketTimeoutException -> "Connection timeout"
        is java.net.ConnectException -> "Connection refused"
        is HttpException -> "Server error"
        is NullPointerException -> "Data is null"
        else -> "Unknown error"
    }
}