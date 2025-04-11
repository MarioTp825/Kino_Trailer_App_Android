package com.tepe.mymovie.ui.dashboard.view

import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tepe.domain.model.movie.MovieGroup
import com.tepe.domain.dashboard.DashboardPageManager
import com.tepe.flutter_integration.contracts.FlutterBridge
import com.tepe.flutter_integration.ui.MovieDetailActivity
import com.tepe.mymovie.ui.components.NetworkContentScreen
import com.tepe.mymovie.ui.dashboard.viewModel.DashboardViewModel
import com.tepe.mymovie.ui.theme.MyMovieTheme

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    NetworkContentScreen(
        networkState = viewModel.dashboardState
    ) {
        DashboardView(
            dashboardPageManager = viewModel.dashboardPageManager,
            modifier = modifier,
            bridge = viewModel.bridge,
            fetch = viewModel::fetchTrailersPage
        )
    }
}

@Composable
fun DashboardView(
    dashboardPageManager: DashboardPageManager,
    bridge: FlutterBridge,
    modifier: Modifier = Modifier,
    fetch: (MovieGroup) -> Unit,
) {
    val activity = LocalActivity.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        activity?.let {
            bridge.initEngine(activity)
        }
        onDispose { }
    }

    MyMovieTheme {
        DashboardContentView(
            content = dashboardPageManager.dashboardPages,
            modifier = modifier,
            fetch = fetch,
            mainDisplay = dashboardPageManager.mainGenre,
        ) { movie ->
            activity?.let {
                activity.startActivity(
                    MovieDetailActivity.buildIntent(movie, activity)
                )
            }
        }
    }
}
