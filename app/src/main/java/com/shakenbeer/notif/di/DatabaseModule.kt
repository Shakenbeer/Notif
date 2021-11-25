package com.shakenbeer.notif.di

import android.content.Context
import androidx.room.Room
import com.shakenbeer.notif.data.db.NotificationDao
import com.shakenbeer.notif.data.db.NotificationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NotificationDatabase {
        return Room.databaseBuilder(
            context,
            NotificationDatabase::class.java, "cocktails"
        ).build()
    }

    @Singleton
    @Provides
    fun provideAlbumDao(db: NotificationDatabase): NotificationDao = db.notificationDao()
}