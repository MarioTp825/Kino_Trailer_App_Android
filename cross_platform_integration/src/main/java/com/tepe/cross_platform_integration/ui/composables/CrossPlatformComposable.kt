package com.tepe.cross_platform_integration.ui.composables

import androidx.activity.addCallback
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.FragmentActivity
import com.tepe.cross_platform_integration.config.di.CrossPlatformBridgeEntryPoint
import com.tepe.cross_platform_integration.model.OnFinishCrossPlatformScreen
import com.tepe.cross_platform_integration.model.ScreenRouter
import dagger.hilt.android.EntryPointAccessors

@Composable
fun <T> CrossPlatformComposable(
    route: ScreenRouter,
    navigateBack: OnFinishCrossPlatformScreen = {},
    parameter: T? = null,
    withNewEngine: Boolean = false,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = LocalActivity.current as? FragmentActivity
        ?: throw IllegalStateException("CrossPlatformComposable can only be used in FragmentActivity")
    val bridge = EntryPointAccessors
        .fromApplication(
            context,
            CrossPlatformBridgeEntryPoint::class.java
        ).getBridge()

    val view = remember { bridge.buildView(route, parameter, withNewEngine) }

    DisposableEffect(Unit) {
        val callBack = activity.onBackPressedDispatcher.addCallback {
            bridge.popBackStack()
        }
        route.setOnFinishScreen(navigateBack)
        onDispose {
            callBack.remove()
            //If we are using a new engine, we need to destroy it or else it will leak
            bridge.shutdownEngine(isNewEngine = true)
            bridge.detachViewFormEngine(view)
        }
    }

    AndroidView(
        modifier = modifier,
        factory = {
            view
        },
    )
}