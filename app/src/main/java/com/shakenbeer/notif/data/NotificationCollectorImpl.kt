package com.shakenbeer.notif.data

import com.shakenbeer.notif.data.db.DbNotification
import com.shakenbeer.notif.data.db.NotificationDao
import com.shakenbeer.notif.domain.Notification
import com.shakenbeer.notif.domain.NotificationCollector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NotificationCollectorImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationCollector {

    private val ioScope = CoroutineScope(Dispatchers.IO)

    override fun addActive(notification: Notification, limit: Int) {
        ioScope.launch {
            notificationDao.addActive(
                notification.map(true), limit = 20
            )
        }
    }

    override fun addAllCurrentlyActive(notifications: List<Notification>, limit: Int) {
        ioScope.launch {
            notificationDao.addAllCurrentlyActive(
                notifications.map { notification ->
                    notification.map(true)
                },
                limit = 20
            )
        }
    }

    override fun deactivate(notification: Notification) {
        ioScope.launch {
            notificationDao.update(
                notification.map(false)
            )
        }
    }

    private fun Notification.map(isActive: Boolean): DbNotification = DbNotification(
        this.id,
        time = this.time,
        packageName = this.packageName,
        title = this.title,
        content = this.content,
        isActive = if (isActive) 1 else 0
    )
}