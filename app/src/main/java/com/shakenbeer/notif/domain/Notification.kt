package com.shakenbeer.notif.domain

data class Notification(
    val id: Int,
    val time: Long,
    val packageName: String,
    val title: String,
    val content: String
)