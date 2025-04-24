package com.tepe.flutter_implementation.ui

import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import com.tepe.cross_platform_integration.model.ScreenRouter
import com.tepe.flutter_implementation.impl.FlutterChannelBinder
import io.flutter.embedding.android.FlutterFragment
import javax.inject.Inject

typealias onBackPressed = () -> Unit

class ReusableFlutterFragment : FlutterFragment() {

    @Inject
    lateinit var channelBinder: FlutterChannelBinder

    private var callBack: OnBackPressedCallback? = null

    override fun onResume() {
        super.onResume()
        callBack = activity?.onBackPressedDispatcher?.addCallback(this) {
            onBackPressed?.invoke()
        }
    }

    override fun onStop() {
        super.onStop()
        callBack?.remove()
    }

    override fun shouldDestroyEngineWithHost(): Boolean = false

    override fun onFlutterUiNoLongerDisplayed() {
        detachFromFlutterEngine()
        super.onFlutterUiNoLongerDisplayed()
    }

    override fun onFlutterUiDisplayed() {
        attachToEngineAutomatically()
        flutterEngine?.let { engine ->
            router?.let {
                channelBinder.buildNewChannel(engine, router)
            }
        }
        super.onFlutterUiDisplayed()
    }

    companion object {

        private var router: ScreenRouter? = null
        private var onBackPressed: onBackPressed? = null

        fun newInstance(
            useNewEngine: Boolean,
            engineID: String,
            initialRoute: String = "/",
            router: ScreenRouter? = null,
            onBackPressed: onBackPressed? = null
        ): ReusableFlutterFragment {
            this.onBackPressed = onBackPressed
            // Use the standard builder to get the args bundle
            val baseFragment = if (useNewEngine) {
                this.router = router
                withNewEngine()
                    .shouldAutomaticallyHandleOnBackPressed(true)
                    .initialRoute(initialRoute)
                    .build()
            } else {
                this.router = null
                withCachedEngine(engineID)
                    .shouldAutomaticallyHandleOnBackPressed(true)
                    .build<FlutterFragment>()
            }

            return ReusableFlutterFragment().also { it.arguments = baseFragment.arguments }
        }
    }
}