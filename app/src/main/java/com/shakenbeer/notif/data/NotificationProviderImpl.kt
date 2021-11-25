package com.shakenbeer.notif.data

import com.shakenbeer.notif.data.db.NotificationDao
import com.shakenbeer.notif.domain.Notification
import com.shakenbeer.notif.domain.NotificationProvider
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class NotificationProviderImpl @Inject constructor(
    private val notificationDao: NotificationDao
) : NotificationProvider {
    override fun getNotifications(activeOnly: Boolean): Flow<List<Notification>> {
        return notificationDao.getAll(if (activeOnly) 1 else 0).map { list ->
            list.map {
                Notification(
                    it.id,
                    it.time,
                    it.packageName,
                    it.title,
                    it.content
                )
            }
        }
    }
}