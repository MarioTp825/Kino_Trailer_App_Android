package com.tepe.mymovie

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.tepe.domain.model.movie.MovieUI
import com.tepe.mymovie.ui.navigation.model.NavigationRoutes.*
import com.tepe.mymovie.ui.navigation.model.navItems
import com.tepe.mymovie.ui.screens.dashboard.view.DashboardScreen
import com.tepe.mymovie.ui.screens.favorites.view.FavoriteScreen
import com.tepe.mymovie.ui.screens.movieDetail.MovieDetailScreen
import com.tepe.mymovie.ui.screens.movieInfo.MovieInfoScreen
import com.tepe.mymovie.ui.screens.appInfo.AppInfoScreen

import com.tepe.mymovie.ui.theme.MyMovieTheme
import com.tepe.mymovie.utils.navType.MovieUINavType
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyMovieTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomNavBar(navController) },
                ) { innerPadding ->
                    MovieNavigation(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    var navigatorIndex by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar {
        navItems.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.name) },
                selected = navigatorIndex == index,
                onClick = {
                    navigatorIndex = index
                    navController.navigate(item.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun MovieNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = Home,
        modifier = modifier,
    ) {
        composable<Home> {
            DashboardScreen {
                navController.navigate(route = MovieDetail(movie = it))
            }
        }

        composable<MovieDetail>(
            typeMap = mapOf(typeOf<MovieUI>() to MovieUINavType),
        ) { backStackEntry ->
            val movie = backStackEntry.toRoute<MovieDetail>().movie
            MovieDetailScreen(movie) {
                navController.popBackStack()
            }
        }

        composable<FavoriteMovieInfo>(
            typeMap = mapOf(typeOf<MovieUI>() to MovieUINavType),
        ) { backStackEntry ->
            val movie = backStackEntry.toRoute<FavoriteMovieInfo>().movie
            MovieInfoScreen(movie) {
                navController.popBackStack()
            }
        }

        composable<Favorite> {
            FavoriteScreen {
                navController.navigate(FavoriteMovieInfo(movie = it))
            }
        }

        composable<AppInfo> {
            AppInfoScreen()
        }
    }
}