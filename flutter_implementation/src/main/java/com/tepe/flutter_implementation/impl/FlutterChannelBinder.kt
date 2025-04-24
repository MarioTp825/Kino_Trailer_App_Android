package com.tepe.flutter_implementation.impl

import com.tepe.cross_platform_integration.model.ScreenRouter
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class FlutterChannelBinder {
    companion object {
        private const val CHANNEL = "app.chanel/movies"
    }

    fun buildNewChannel(engine: FlutterEngine, screenRouter: ScreenRouter?): MethodChannel {
        return MethodChannel(engine.dartExecutor.binaryMessenger, CHANNEL).apply {
            setMethodCallHandler { call, result ->
                val data = call.argument<String>("data")?.takeIf { it != "null" && it.isNotBlank() }
                if (call.method == "finish") {
                    screenRouter?.onFinish?.invoke(data)
                }
                screenRouter?.callMethod?.forEach { method ->
                    if (method == call.method) {
                        screenRouter.receivedMessage(method, data)
                        result.success(null)
                        return@setMethodCallHandler
                    }
                }
                result.notImplemented()
            }
        }
    }
}