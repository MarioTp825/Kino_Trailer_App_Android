package com.tepe.mymovie.config

import android.app.Application
import com.tepe.cross_platform_integration.contracts.CrossPlatformBridge
import com.tepe.data.database.config.DataBaseAccess
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyMovieApp: Application() {

    @Inject
    lateinit var crossPlatformBridge: CrossPlatformBridge

    @Inject
    lateinit var dataBase: DataBaseAccess

    override fun onCreate() {
        super.onCreate()
        crossPlatformBridge.startEngine()

    }
}