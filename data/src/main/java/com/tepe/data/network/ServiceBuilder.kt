package com.tepe.data.network

import com.tepe.data.dashboard.KinoCheckTrailerService
import com.tepe.data.dashboard.createGsonKinoTrailer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KClass

class ServiceBuilder(
    baseUrl: String
) {
    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val kinoTrailerRetrofit = Retrofit
        .Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(createGsonKinoTrailer()))
        .build()

    fun <T : Any> buildService(service: KClass<T>): T {
        return if (service == KinoCheckTrailerService::class) {
            kinoTrailerRetrofit.create(service.java)
        } else {
            retrofit.create(service.java)
        }
    }
}