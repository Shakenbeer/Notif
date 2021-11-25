package com.shakenbeer.notif.data.db

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import kotlinx.coroutines.flow.Flow

@Dao
abstract class NotificationDao {

    @Query("SELECT * FROM notification where isActive >= :activeOnly order by time DESC")
    abstract fun getAll(activeOnly: Int): Flow<List<DbNotification>>

    @Query("SELECT * FROM notification")
    abstract fun getAllSync(): List<DbNotification>

    @Insert(onConflict = REPLACE)
    abstract fun insert(notification: DbNotification)

    @Insert(onConflict = REPLACE)
    abstract fun insertAll(notifications: List<DbNotification>)

    @Delete
    abstract fun deleteAll(notifications: List<DbNotification>)

    @Update
    abstract fun update(notification: DbNotification)

    @Update
    abstract fun updateAll(notifications: List<DbNotification>)

    @Transaction
    open suspend fun addActive(notification: DbNotification, limit: Int) {
        removeOldToMeetLimit(limit)
        insert(notification)
    }

    @Transaction
    open suspend fun addAllCurrentlyActive(notifications: List<DbNotification>, limit: Int) {
        val toInsert = if (notifications.size > limit) {
            notifications.sortedByDescending { it.time }.take(limit)
        } else {
            notifications
        }
        removeOldToMeetLimit(toInsert, limit)
        deactivateOld(notifications)
        insertAll(toInsert)
    }

    private fun removeOldToMeetLimit(limit: Int) {
        val all = getAllSync()
        if (all.size >= limit) {
            val toDelete = all.sortedBy { it.time }.take(all.size - limit + 1)
            deleteAll(toDelete)
        }
    }

    private fun removeOldToMeetLimit(
        toInsert: List<DbNotification>,
        limit: Int
    ) {
        val all = getAllSync()
        if (all.size + toInsert.size > limit) {
            val toDelete = all.sortedBy { it.time }.take(all.size - limit + toInsert.size)
            deleteAll(toDelete)
        }
    }

    private fun deactivateOld(notifications: List<DbNotification>) {
        val all = getAllSync()
        val deactivated = all - notifications
        updateAll(deactivated.map { it.copy(isActive = 0) })
    }
}
