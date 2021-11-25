package com.shakenbeer.notif.domain

import kotlinx.coroutines.flow.Flow

interface NotificationProvider {
    fun getNotifications(activeOnly: Boolean): Flow<List<Notification>>
}