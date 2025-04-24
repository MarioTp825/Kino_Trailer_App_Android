package com.tepe.flutter_implementation.config.di

import com.tepe.cross_platform_integration.contracts.CrossPlatformBridge
import com.tepe.flutter_implementation.impl.FlutterBridgeImpl
import com.tepe.flutter_implementation.impl.FlutterChannelBinder
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FlutterAppModule {

    @Binds
    @Singleton
    abstract fun bindFlutterChannelBinder(channelBinder: FlutterChannelBinder): FlutterChannelBinder

    @Binds
    @Singleton
    abstract fun bindFlutterBridge(impl: FlutterBridgeImpl): CrossPlatformBridge

}