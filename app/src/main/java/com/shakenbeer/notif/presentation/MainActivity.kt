package com.shakenbeer.notif.presentation

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.shakenbeer.notif.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val mainViewModel: MainViewModel by viewModels()

    private val notificationAdapter = NotificationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.notificationsRecyclerView.adapter = notificationAdapter

        binding.enableButton.setOnClickListener {
            startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
        }

        mainViewModel.notifications.observe(this) {
            binding.emptyStateTextView.isVisible = it.isEmpty()
            notificationAdapter.submitList(it)
        }

        binding.onlyActiveCheckBox.setOnCheckedChangeListener { _, isChecked ->
            mainViewModel.onFilter(isChecked)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.enableGroup.isVisible = !isNotificationServiceEnabled()
        binding.notificationGroup.isVisible = isNotificationServiceEnabled()
    }

    private fun isNotificationServiceEnabled(): Boolean {
        val listeners = Settings.Secure.getString(
            contentResolver,
            ENABLED_NOTIFICATION_LISTENERS
        )
        if (listeners.isNotEmpty()) {
            return listeners.split(":")
                .map { ComponentName.unflattenFromString(it)?.packageName }
                .contains(packageName)
        }
        return false
    }

    companion object {
        private const val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
        private const val ACTION_NOTIFICATION_LISTENER_SETTINGS =
            "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    }
}