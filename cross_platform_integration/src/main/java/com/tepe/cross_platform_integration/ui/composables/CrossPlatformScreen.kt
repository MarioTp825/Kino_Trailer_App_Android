package com.tepe.cross_platform_integration.ui.composables

import android.view.View.generateViewId
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentContainerView
import com.tepe.cross_platform_integration.config.di.CrossPlatformBridgeEntryPoint
import com.tepe.cross_platform_integration.model.OnFinishCrossPlatformScreen
import com.tepe.cross_platform_integration.model.ScreenRouter
import dagger.hilt.android.EntryPointAccessors

@Composable
fun CrossPlatformScreen(
    route: ScreenRouter,
    navigateBack: OnFinishCrossPlatformScreen = {},
    parameter: Any? = null,
    withNewEngine: Boolean = false,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val activity = LocalActivity.current as? FragmentActivity
        ?: throw IllegalStateException("CrossPlatformFlutterComposable can only be used in FragmentActivity")
    val bridge = remember {
        EntryPointAccessors
            .fromApplication(
                context,
                CrossPlatformBridgeEntryPoint::class.java
            )
            .getBridge()
    }

    val crossPlatformFragment = remember(route, parameter) {
        bridge.buildFragment(route, parameter, withNewEngine)
    }

    var containerId by remember { mutableIntStateOf(0) }

    AndroidView(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier),
        factory = { ctx ->
            FragmentContainerView(ctx).apply {
                containerId = generateViewId()
                id = containerId
            }
        }
    )

    DisposableEffect(containerId) {
        activity.supportFragmentManager.beginTransaction()
            .replace(containerId, crossPlatformFragment)
            .commitNow()

        route.setOnFinishScreen(onFinish = navigateBack)

        onDispose {
            activity
                .supportFragmentManager
                .beginTransaction()
                .remove(crossPlatformFragment)
                .commitNow()
        }
    }
}