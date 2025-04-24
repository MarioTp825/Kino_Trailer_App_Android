package com.tepe.cross_platform_integration.contracts

import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import com.tepe.cross_platform_integration.model.ScreenRouter

interface CrossPlatformBridge {

    /**
     * @param parameter will send data through channel
     * */
    fun <T> buildActivityIntent(
        route: ScreenRouter,
        parameter: T? = null,
        withNewEngine: Boolean = false
    ): Intent

    /**
     * @param parameter will send data through channel
     * */
    fun <T> buildFragment(
        route: ScreenRouter,
        parameter: T? = null,
        withNewEngine: Boolean = false
    ): Fragment

    /**
     * @param parameter will send data through channel
     * */
    fun <T> buildView(
        route: ScreenRouter,
        parameter: T? = null,
        withNewEngine: Boolean = false
    ): View

    /**
     * Force engine Start for warm up
     * */
    fun startEngine()

    fun shutdownEngine(isNewEngine: Boolean = false)

    fun popBackStack()

    /**
     * When using an Activity or Fragment, you cannot send data through the channel
     * with a new engine because it creates a new instance internally. When using a View,
     * you may send data through the channel with a new engine.
     * */
    fun <T> invokeCrossPlatformMethod(data: T? = null)

    fun detachFragmentFormEngine(fragment: Fragment)
    fun detachViewFormEngine(view: View)
}