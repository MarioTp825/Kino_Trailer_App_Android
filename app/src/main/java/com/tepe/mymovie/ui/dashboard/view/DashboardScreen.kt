package com.tepe.mymovie.ui.dashboard.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tepe.domain.model.movie.MovieGroup
import com.tepe.domain.dashboard.DashboardPageManager
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
            fetch = viewModel::fetchTrailersPage
        )
    }
}

@Composable
fun DashboardView(
    dashboardPageManager: DashboardPageManager,
    modifier: Modifier = Modifier,
    fetch: (MovieGroup) -> Unit,
) {
    val context = LocalContext.current
    MyMovieTheme {
        DashboardContentView(
            content = dashboardPageManager.dashboardPages,
            modifier = modifier,
            fetch = fetch,
            mainDisplay = dashboardPageManager.mainGenre,
        ) {
            //TODO Launch flutter Activity
        }
    }
}
