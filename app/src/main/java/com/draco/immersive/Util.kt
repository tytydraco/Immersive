package com.draco.immersive

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat

/* Suffix to secure setting that excludes SystemUI from immersive mode */
private const val excludeSystemUI = ",-com.android.systemui"

fun getSuffix(context: Context, sharedPrefs: SharedPreferences): String {
    var suffix = if (sharedPrefs.getBoolean(context.getString(R.string.pref_exclude_systemui), false))
        excludeSystemUI
    else
        ""
    val blocklistIds = sharedPrefs.getStringSet("blocklist_ids", setOf())
    if (blocklistIds != null) for (id in blocklistIds) {
        suffix += ",-$id"
    }
    return suffix
}

fun hasPermissions(context: Context): Boolean {
    val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SECURE_SETTINGS)
    return permissionCheck == PackageManager.PERMISSION_GRANTED
}

class AppInfo {
    var id = ""
    var name = ""
    var img: Drawable? = null
}