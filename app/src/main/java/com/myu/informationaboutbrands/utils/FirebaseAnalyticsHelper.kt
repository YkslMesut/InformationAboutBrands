package com.myu.informationaboutbrands.utils

import android.os.Bundle
import android.util.Log
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseAnalyticsHelper @Inject constructor() : AnalyticsHelper {

    private var firebaseAnalytics = Firebase.analytics
    private var firebaseCrashlytics = Firebase.crashlytics


    override fun sendScreenView(screenName: String, screenClass: String?) {
        firebaseAnalytics.run {
            val bundle = Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                putString(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass ?: screenName)
            }
            logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
            Log.d("FirebaseAnalytics", "Screen View recorded: $screenName")
        }
    }

    override fun logUiEvent(itemId: String, action: String) {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
            param(FirebaseAnalytics.Param.ITEM_ID, itemId)
            param(FirebaseAnalytics.Param.CONTENT_TYPE, FA_CONTENT_TYPE_UI_EVENT)
            param(FA_KEY_UI_ACTION, action)
        }
        Log.d("FirebaseAnalytics", "Event recorded for $itemId, $action")
    }

    override fun reportNonFatalError(exception: Throwable) {
        firebaseCrashlytics.recordException(exception)
    }

    override fun setUserId(userId: String) {
        firebaseCrashlytics.setUserId(userId)
    }

    companion object {
        private const val UPROP_USER_SIGNED_IN = "user_signed_in"

        private const val FA_CONTENT_TYPE_SCREENVIEW = "screen"
        private const val FA_KEY_UI_ACTION = "ui_action"
        private const val FA_CONTENT_TYPE_UI_EVENT = "ui event"
    }
}