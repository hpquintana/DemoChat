package io.github.hpquintana.demochat.fcm

import android.app.IntentService
import android.content.Intent
import android.util.Log


class NotificationActionService : IntentService(NotificationActionService::class.java.simpleName) {
    private val TAG = "IntentService"

    override fun onHandleIntent(intent: Intent) {
        val action = intent.action

        Log.w(TAG, action)
    }
}