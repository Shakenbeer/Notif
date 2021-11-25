package com.shakenbeer.notif.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DbNotification::class], version = 1, exportSchema = true)
abstract class NotificationDatabase : RoomDatabase() {
    abstract fun notificationDao(): NotificationDao
}