package com.tepe.cross_platform_integration.ui.modifiers

import androidx.compose.foundation.clickable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.debugInspectorInfo
import com.tepe.cross_platform_integration.config.di.CrossPlatformBridgeEntryPoint
import com.tepe.cross_platform_integration.model.OnFinishCrossPlatformScreen
import com.tepe.cross_platform_integration.model.ScreenRouter
import dagger.hilt.android.EntryPointAccessors

inline fun <reified T> Modifier.launchCrossPlatformActivity(
    route: ScreenRouter,
    parameter: T? = null,
    withNewEngine: Boolean = false,
    crossinline onFinish: OnFinishCrossPlatformScreen = {},
) = composed(
    inspectorInfo = debugInspectorInfo {
        name = "openFlutterActivity"
        properties["route"] = route
        properties["params"] = parameter
        properties["withNewEngine"] = withNewEngine
    }
) {
    val context = LocalContext.current
    val bridge = remember {
        EntryPointAccessors
            .fromApplication(
                context,
                CrossPlatformBridgeEntryPoint::class.java
            )
            .getBridge()
    }

    LaunchedEffect(Unit) {
        route.setOnFinishScreen{
            onFinish(it)
        }
    }

    this.then(
        Modifier.clickable {
            val intent = bridge.buildActivityIntent(
                route = route,
                parameter = parameter,
                withNewEngine = withNewEngine
            )
            context.startActivity(intent)
        }
    )
}