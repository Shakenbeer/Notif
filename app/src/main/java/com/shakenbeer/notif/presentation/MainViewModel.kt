package com.shakenbeer.notif.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.shakenbeer.notif.domain.NotificationProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notificationProvider: NotificationProvider
) : ViewModel() {

    private val filterTrigger = MutableSharedFlow<Boolean>(replay = 1).also { it.tryEmit(false) }

    val notifications = filterTrigger.flatMapLatest { activeOnly ->
        notificationProvider.getNotifications(activeOnly)
    }.map { list ->
        list.map {
            DisplayNotification(it)
        }
    }.asLiveData()

    fun onFilter(activeOnly: Boolean) {
        filterTrigger.tryEmit(activeOnly)
    }

}