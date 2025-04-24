package com.tepe.mymovie.ui.screens.movieDetail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.tepe.cross_platform_integration.model.OnFinishCrossPlatformScreen
import com.tepe.cross_platform_integration.ui.composables.CrossPlatformScreen
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.config.di.entryPoints.ScreenRouteEntryPoint
import dagger.hilt.android.EntryPointAccessors

@Composable
fun MovieDetailScreen(movieUI: MovieUI, onNavigateBack: OnFinishCrossPlatformScreen) {
    val context = LocalContext.current
    val router = remember {
        EntryPointAccessors
            .fromApplication(
                context,
                ScreenRouteEntryPoint::class.java
            )
            .getMovieDetailScreen()
    }
    CrossPlatformScreen(
        route = router,
        parameter = movieUI,
        navigateBack = onNavigateBack
    )
}
