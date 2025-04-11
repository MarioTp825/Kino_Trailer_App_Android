package com.tepe.flutter_integration.impl

import android.content.Context
import android.content.Intent
import android.view.View
import com.tepe.flutter_integration.contracts.FlutterBridge
import com.tepe.flutter_integration.contracts.MovieParams
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterView
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.plugin.common.MethodChannel

class FlutterBridgeImpl: FlutterBridge {
    private val cacheID = "flutter_engine_cache_id"
    private val chanel = "app.chanel/movies"
    private var engine: FlutterEngine? = null

    override fun initEngine(context: Context) {
        if(engine != null) return
        engine = FlutterEngine(context.applicationContext)
        engine?.navigationChannel?.setInitialRoute("/")
        engine?.dartExecutor?.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        FlutterEngineCache.getInstance().put(cacheID, engine)
    }

    override fun buildIntent(context: Context): Intent {
        return FlutterActivity.createDefaultIntent(context)
    }

    override fun getView(context: Context): View? {
        val flEngine = engine ?: return null

        val view = FlutterView(context).apply {
            attachToFlutterEngine(flEngine)
            flEngine.lifecycleChannel.appIsResumed()
        }
        engine?.lifecycleChannel?.appIsResumed()

        return view
    }

    override fun sendData(data: String, param: MovieParams) {

        engine?.dartExecutor?.binaryMessenger?.let { binaryMessenger ->
            val chanel = MethodChannel(binaryMessenger, chanel)
            chanel.invokeMethod(param.method, data)
        }

    }
}