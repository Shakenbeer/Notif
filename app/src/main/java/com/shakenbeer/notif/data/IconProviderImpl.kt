package com.shakenbeer.notif.data

import android.app.Application
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import com.shakenbeer.notif.R
import com.shakenbeer.notif.appicon.IconProvider
import javax.inject.Inject


class IconProviderImpl @Inject constructor(private val application: Application): IconProvider {

    override fun getAppIcon(packageName: String): Drawable? {
        return try {
            application.packageManager.getApplicationIcon(packageName)
        } catch (e: PackageManager.NameNotFoundException) {
            AppCompatResources.getDrawable(application, R.drawable.ic_lock_question)
        }
    }
}