package com.tepe.cross_platform_integration.model

typealias OnFinishCrossPlatformScreen = (String?) -> Unit

abstract class ScreenRouter {
    abstract val route: String

    open val sendMethod: String? get() = null
    open val callMethod: List<String> get()= listOf()

    var onFinish: OnFinishCrossPlatformScreen? = null
        private set

    /**
     * This method will be called on the background when the CrossPlatform
     * engine sends a message through the channel
     * */
    open fun receivedMessage(method: String, message: String?) = Unit

    fun setOnFinishScreen(onFinish: OnFinishCrossPlatformScreen) {
        this.onFinish = onFinish
    }
}