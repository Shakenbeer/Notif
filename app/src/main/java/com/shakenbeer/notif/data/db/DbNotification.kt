package com.shakenbeer.notif.data.db

import androidx.room.Entity

@Entity(tableName = "notification", primaryKeys = ["id", "packageName"])
data class DbNotification(
    val id: Int,
    val time: Long,
    val packageName: String,
    val title: String,
    val content: String,
    val isActive: Int
)