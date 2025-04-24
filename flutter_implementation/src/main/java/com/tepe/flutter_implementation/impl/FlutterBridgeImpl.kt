package com.tepe.flutter_implementation.impl

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.tepe.cross_platform_integration.contracts.CrossPlatformBridge
import com.tepe.cross_platform_integration.model.ScreenRouter
import com.tepe.flutter_implementation.ui.ReusableFlutterFragment
import dagger.hilt.android.qualifiers.ApplicationContext
import io.flutter.embedding.android.FlutterActivity.withCachedEngine
import io.flutter.embedding.android.FlutterActivity.withNewEngine
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel
import javax.inject.Inject

class FlutterBridgeImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : CrossPlatformBridge {
    companion object {
        private const val ENGINE_CACHE_ID = "flutter_engine_cache_id"
        private const val CHANNEL = "app.chanel/movies"
    }

    private val gson: Gson = Gson()

    private val flutterEngine by lazy {
        FlutterEngine(context).apply {
            dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )
            FlutterEngineCache.getInstance().put(ENGINE_CACHE_ID, this)
        }
    }
    private var screenRouter: ScreenRouter? = null
    private val channel: MethodChannel = buildNewChannel(engine = flutterEngine)

    //New engine management
    private var newEngine: FlutterEngine? = null
    private var newChannel: MethodChannel? = null

    override fun startEngine() {
        flutterEngine
    }

    override fun shutdownEngine(isNewEngine: Boolean) {
        if (isNewEngine) {
            newEngine?.destroy()
            newEngine = null
        } else {
            flutterEngine.destroy()
            FlutterEngineCache.getInstance().remove(ENGINE_CACHE_ID)
        }
    }

    override fun popBackStack() {
        val engine = newEngine ?: flutterEngine
        engine.navigationChannel.popRoute()
    }

    override fun <T> buildActivityIntent(
        route: ScreenRouter,
        parameter: T?,
        withNewEngine: Boolean
    ): Intent {
        validateNewEngine(withNewEngine, parameter)
        screenRouter = route

        channel.invokeMethod("selfPopBack", null)
        val initRoute = setUpParamsAndGetInitialRoute(parameter)
        return if (withNewEngine) {
            withNewEngine().initialRoute(initRoute).build(context)
        } else {
            withCachedEngine(ENGINE_CACHE_ID).build(context)
        }
    }

    override fun <T> buildFragment(
        route: ScreenRouter,
        parameter: T?,
        withNewEngine: Boolean
    ): Fragment {
        screenRouter = route
        val initRoute = setUpParamsAndGetInitialRoute(parameter)
        return ReusableFlutterFragment.newInstance(
            useNewEngine = withNewEngine,
            engineID = ENGINE_CACHE_ID,
            initialRoute = initRoute,
            route,
        ) {
            popBackStack()
        }
    }

    override fun <T> buildView(route: ScreenRouter, parameter: T?, withNewEngine: Boolean): View {
        val engine = if (withNewEngine) {
            buildNewEngine(route)
        } else {
            flutterEngine
        }
        screenRouter = route

        parameter?.let {
            invokeCrossPlatformMethod(parameter)
        }

        return FlutterView(context).apply {
            attachToFlutterEngine(engine)
            engine.lifecycleChannel.appIsResumed()
        }
    }

    override fun <T> invokeCrossPlatformMethod(data: T?) {
        val method = screenRouter?.sendMethod ?: return
        val channel = newChannel ?: this.channel

        val serializedData = data?.let { gson.toJson(data) }
        channel.invokeMethod(method, serializedData)
    }

    override fun detachFragmentFormEngine(fragment: Fragment) {
        val flutterFragment = fragment as? FlutterFragment ?: return
        flutterFragment.detachFromFlutterEngine()
    }

    override fun detachViewFormEngine(view: View) {
        val flutterView = view as? FlutterView ?: return
        flutterView.detachFromFlutterEngine()
    }

    private fun <T> setUpParamsAndGetInitialRoute(parameter: T?): String {

        // If we need to share data across screens, the home route will take the method and
        // redirect the screen when obtaining the data through the channel. That is why we
        // set the initial route to "/"
        if (screenRouter?.sendMethod == null) {
            flutterEngine.navigationChannel.setInitialRoute(screenRouter?.route ?: "/")
        } else {
            flutterEngine.navigationChannel.setInitialRoute("/")
        }

        screenRouter?.sendMethod ?: return screenRouter?.route ?: "/"
        invokeCrossPlatformMethod(parameter)
        return screenRouter?.route ?: "/"
    }

    private fun <T> validateNewEngine(withNewEngine: Boolean, parameter: T?) {
        if (withNewEngine && parameter != null) {
            throw IllegalArgumentException("You cannot pass parameter with new engine, because the engine will be created again")
        }
    }

    private fun buildNewEngine(route: ScreenRouter): FlutterEngine {
        return FlutterEngine(context).apply {
            navigationChannel.setInitialRoute(route.route)
            dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )

            newChannel = buildNewChannel(this)
            newEngine = this

        }
    }

    private fun buildNewChannel(engine: FlutterEngine): MethodChannel {
        return MethodChannel(engine.dartExecutor.binaryMessenger, CHANNEL).apply {
            setMethodCallHandler { call, result ->
                val data = call.argument<String>("data")?.takeIf { it != "null" && it.isNotBlank() }
                if (call.method == "finish") {
                    screenRouter?.onFinish?.invoke(data)
                    result.success(null)
                    return@setMethodCallHandler
                }
                if (call.method == "refresh") {
                    engine.lifecycleChannel.appIsPaused()
                    engine.lifecycleChannel.appIsResumed()
                    result.success(null)
                    return@setMethodCallHandler
                }
                screenRouter?.callMethod?.forEach { method ->
                    if (method == call.method) {
                        screenRouter?.receivedMessage(method, data)
                        result.success(null)
                        return@setMethodCallHandler
                    }
                }
                result.notImplemented()
            }
        }
    }
}