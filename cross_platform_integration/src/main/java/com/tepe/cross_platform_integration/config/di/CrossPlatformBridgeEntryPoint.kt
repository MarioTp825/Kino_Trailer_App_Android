package com.tepe.cross_platform_integration.config.di

import com.tepe.cross_platform_integration.contracts.CrossPlatformBridge
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface CrossPlatformBridgeEntryPoint {
    fun getBridge(): CrossPlatformBridge
}