package com.tepe.mymovie.ui.navigation.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItem<T: Any>(val route: T, val name: String, val icon: ImageVector)

val navItems = listOf(
    NavigationItem(
        route = NavigationRoutes.Home,
        name = "Home",
        icon = Icons.Outlined.Home
    ),
    NavigationItem(
        route = NavigationRoutes.Favorite,
        name = "Favorite",
        icon = Icons.Filled.Favorite
    ),
    NavigationItem(
        route = NavigationRoutes.AppInfo,
        name = "App Info",
        icon = Icons.Filled.Info
    )
)