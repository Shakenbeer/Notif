package com.shakenbeer.notif.domain

interface NotificationCollector {
    fun addActive(notification: Notification, limit: Int)
    fun addAllCurrentlyActive(notifications: List<Notification>, limit: Int)
    fun deactivate(notification: Notification)
}