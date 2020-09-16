package com.draco.immersive

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable

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

class AppInfo {
    var id = ""
    var name = ""
    var img: Drawable? = null
}