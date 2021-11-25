package com.shakenbeer.notif.di

import com.shakenbeer.notif.appicon.IconProvider
import com.shakenbeer.notif.data.NotificationCollectorImpl
import com.shakenbeer.notif.data.NotificationProviderImpl
import com.shakenbeer.notif.data.IconProviderImpl
import com.shakenbeer.notif.domain.NotificationCollector
import com.shakenbeer.notif.domain.NotificationProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NotificationModule{

    @Binds
    abstract fun bindNotificationProvider(
        notificationProviderImpl: NotificationProviderImpl
    ): NotificationProvider

    @Binds
    abstract fun bindNotificationCollector(
        notificationCollectorImpl: NotificationCollectorImpl
    ): NotificationCollector

    @Binds
    abstract fun bindIconProvider(
        iconProviderImpl: IconProviderImpl
    ): IconProvider

}