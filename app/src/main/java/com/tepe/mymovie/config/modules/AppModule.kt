package com.tepe.mymovie.config.modules

import com.tepe.data.dashboard.DashboardRepositoryImpl
import com.tepe.data.dashboard.KinoCheckTrailerService
import com.tepe.data.network.ServiceBuilder
import com.tepe.domain.dashboard.DashboardPageManager
import com.tepe.domain.repository.DashboardRepository
import com.tepe.flutter_integration.contracts.FlutterBridge
import com.tepe.flutter_integration.impl.FlutterBridgeImpl
import com.tepe.mymovie.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesServiceBuilder(): ServiceBuilder {
        return ServiceBuilder(baseUrl = BuildConfig.BASE_URL)
    }

    @Provides
    @Singleton
    fun providesDashboardRepository(
        serviceBuilder: ServiceBuilder
    ): DashboardRepository {
        val api = serviceBuilder.buildService(KinoCheckTrailerService::class)
        return DashboardRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesDashboardPageManager(): DashboardPageManager {
        return DashboardPageManager()
    }

    @Provides
    @Singleton
    fun providesFlutterBridge(): FlutterBridge {
        return FlutterBridgeImpl()
    }
}