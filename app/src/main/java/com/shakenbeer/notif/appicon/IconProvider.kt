package com.shakenbeer.notif.appicon

import android.graphics.drawable.Drawable

interface IconProvider {
    fun getAppIcon(packageName: String): Drawable?
}