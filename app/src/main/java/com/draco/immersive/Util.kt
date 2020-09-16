package com.draco.immersive

import android.content.Context
import android.content.SharedPreferences

/* Suffix to secure setting that excludes SystemUI from immersive mode */
private const val excludeSystemUI = ",-com.android.systemui"

fun getSuffix(context: Context, sharedPrefs: SharedPreferences): String {
    return if (sharedPrefs.getBoolean(context.getString(R.string.pref_exclude_systemui), false))
        excludeSystemUI
    else
        ""
}