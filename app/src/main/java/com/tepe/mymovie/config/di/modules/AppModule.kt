package com.tepe.mymovie.config.di.modules

import android.content.Context
import com.tepe.data.dashboard.DashboardRepositoryImpl
import com.tepe.data.dashboard.KinoCheckTrailerService
import com.tepe.data.database.config.DataBaseAccess
import com.tepe.data.database.dao.MovieDao
import com.tepe.data.movie.repository.MovieLocalRepositoryImpl
import com.tepe.data.network.ServiceBuilder
import com.tepe.domain.dashboard.DashboardPageManager
import com.tepe.domain.repository.DashboardRepository
import com.tepe.domain.repository.MovieLocalRepository
import com.tepe.mymovie.BuildConfig
import com.tepe.mymovie.config.screenRouters.AppInfoScreenRouter
import com.tepe.mymovie.config.screenRouters.MovieDetailScreenRouter
import com.tepe.mymovie.config.screenRouters.MovieInfoScreenRouter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesDatabaseAccess(
        @ApplicationContext context: Context
    ): DataBaseAccess {
        return DataBaseAccess(context)
    }

    @Provides
    @Singleton
    fun providesServiceBuilder(): ServiceBuilder {
        return ServiceBuilder(baseUrl = BuildConfig.BASE_URL)
    }

    @Provides
    fun providesMovieDao(
        databaseAccess: DataBaseAccess
    ): MovieDao {
        return databaseAccess.getMovieDao()
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
    fun providesMovieLocalRepository(movieDao: MovieDao): MovieLocalRepository {
        return MovieLocalRepositoryImpl(movieDao)
    }

    @Provides
    @Singleton
    fun providesDashboardPageManager(): DashboardPageManager {
        return DashboardPageManager()
    }

    @Provides
    fun providesMovieDetailScreenRouter(
        repository: MovieLocalRepository
    ): MovieDetailScreenRouter {
        return MovieDetailScreenRouter(repository)
    }

    @Provides
    fun providesMovieInfoScreenRouter(
        repository: MovieLocalRepository
    ): MovieInfoScreenRouter {
        return MovieInfoScreenRouter(repository)
    }

    @Provides
    fun providesAppInfoScreenRouter(): AppInfoScreenRouter {
        return AppInfoScreenRouter()
    }
}