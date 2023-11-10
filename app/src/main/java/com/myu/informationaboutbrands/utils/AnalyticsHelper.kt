package com.myu.informationaboutbrands.utils

interface AnalyticsHelper {
    fun sendScreenView(screenName: String, screenClass: String? = null)

    fun logUiEvent(itemId: String, action: String)

    fun reportNonFatalError(exception: Throwable)

    fun setUserId(userId: String)
}

/** Actions that should be used when sending analytics events */
object AnalyticsActions {
    // UI Actions
    const val CLICK = "Clicked"
    const val SWITCH = "switch"
    const val TAB_CLICK = "Tab Clicked"
}