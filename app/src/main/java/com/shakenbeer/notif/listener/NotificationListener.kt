package com.shakenbeer.notif.listener

import android.app.Notification.*
import android.content.Intent
import android.os.IBinder
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.shakenbeer.notif.domain.Notification
import com.shakenbeer.notif.domain.NotificationCollector
import com.shakenbeer.notif.domain.ON_SCREEN_AMOUNT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListener : NotificationListenerService() {

    @Inject
    lateinit var collector: NotificationCollector

    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }

    override fun onListenerConnected() {
        val active = activeNotifications
        if (active.isNotEmpty()) {
            collector.addAllCurrentlyActive(active.map { sbn ->
                sbn.map()
            }, ON_SCREEN_AMOUNT)
        }
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        collector.addActive(sbn.map(), ON_SCREEN_AMOUNT)
    }

    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        collector.deactivate(sbn.map())
    }

    private fun StatusBarNotification.map(): Notification {
        val extras = this.notification.extras

        val notification = Notification(
            this.id,
            time = this.postTime,
            packageName = this.packageName,
            title = extras.getString(EXTRA_TITLE) ?: this.packageName,
            content = (extras.getString(EXTRA_TEXT) ?: "") + (extras.getString(EXTRA_SUB_TEXT)
                ?: "")
        )

        Log.d(
            "NOTIF_DEBUG",
            "Listener onMap: ${this.postTime} - ${this.id} - ${this.packageName} - ${this.notification.shortcutId} -- $notification"
        )

        return notification
    }
}