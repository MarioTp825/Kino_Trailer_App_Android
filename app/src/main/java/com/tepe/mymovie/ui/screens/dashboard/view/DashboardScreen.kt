package com.tepe.mymovie.ui.screens.dashboard.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.tepe.domain.dashboard.DashboardPageManager
import com.tepe.domain.model.movie.MovieGroup
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.ui.components.NetworkContentScreen
import com.tepe.mymovie.ui.screens.dashboard.viewModel.DashboardViewModel
import com.tepe.mymovie.ui.theme.MyMovieTheme

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navigateToDetail: (MovieUI) -> Unit = {},
) {
    NetworkContentScreen(
        networkState = viewModel.dashboardState
    ) {
        DashboardView(
            dashboardPageManager = viewModel.dashboardPageManager,
            modifier = modifier,
            onClick = { movie ->
                viewModel.isMovieFavorite(movie, navigateToDetail)
            },
            fetch = viewModel::fetchTrailersPage
        )
    }
}

@Composable
fun DashboardView(
    dashboardPageManager: DashboardPageManager,
    modifier: Modifier = Modifier,
    onClick: (MovieUI) -> Unit,
    fetch: (MovieGroup) -> Unit,
) {
    MyMovieTheme {
        DashboardContentView(
            content = dashboardPageManager.dashboardPages,
            modifier = modifier,
            fetch = fetch,
            mainDisplay = dashboardPageManager.mainGenre,
            onClick = onClick
        )
    }
}
