package com.shakenbeer.notif.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shakenbeer.notif.R
import com.shakenbeer.notif.databinding.ItemNotificationBinding

class NotificationAdapter :
    ListAdapter<DisplayNotification, NotificationViewHolder>(NotificationDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return NotificationViewHolder(ItemNotificationBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            titleTextView.text = item.notification.title
            contentTextView.text = item.notification.content
            appIconImageView.setImageDrawable(getAppIcon(root.context, item.notification.packageName))
        }
    }

    private fun getAppIcon(context: Context, packageName: String): Drawable? {
        return try {
            context.packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            AppCompatResources.getDrawable(context, R.drawable.ic_lock_question)
        }
    }
}

class NotificationDiffCallback : DiffUtil.ItemCallback<DisplayNotification>() {
    override fun areItemsTheSame(
        oldItem: DisplayNotification,
        newItem: DisplayNotification
    ): Boolean {
        return oldItem.notification.id == newItem.notification.id &&
                oldItem.notification.packageName == newItem.notification.packageName &&
                oldItem.notification.time == newItem.notification.time
    }

    override fun areContentsTheSame(
        oldItem: DisplayNotification,
        newItem: DisplayNotification
    ): Boolean {
        return oldItem.notification.packageName == newItem.notification.packageName &&
                oldItem.notification.title == newItem.notification.title &&
                oldItem.notification.content == newItem.notification.content
    }
}

class NotificationViewHolder(val binding: ItemNotificationBinding) :
    RecyclerView.ViewHolder(binding.root)

