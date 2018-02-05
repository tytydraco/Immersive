package com.draco.immersive

import android.Manifest
import android.content.pm.PackageManager
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog

val SETTING_GLOBAL: Int = 0
val SETTING_SECURE: Int = 1
val SETTING_SYSTEM: Int = 2

internal fun MainActivity.permissionCheck() {
    val permissionCheck = ContextCompat.checkSelfPermission(this,
            Manifest.permission.WRITE_SECURE_SETTINGS)
    if (permissionCheck == PackageManager.PERMISSION_DENIED) {
        val error = AlertDialog.Builder(this)
        error.setTitle("Missing Permissions")
        error.setMessage("To allow this app to work, you must run an ADB command via your computer.\n\nadb shell pm grant " + applicationContext.packageName + " android.permission.WRITE_SECURE_SETTINGS")
        error.setPositiveButton("Ok") { _, _ -> permissionCheck() }
        error.setNegativeButton("Close") { _, _ -> System.exit(0) }
        error.setCancelable(false)
        error.show()
    }
}

fun MainActivity.getSetting(group: Int, name: String, type: Any): Any? {
    if (group == SETTING_GLOBAL) {
        if (type == Int) {
            return Settings.Global.getInt(contentResolver, name)
        } else if (type == String) {
            return Settings.Global.getString(contentResolver, name)
        } else if (type == Float || type == Double) {
            return Settings.Global.getFloat(contentResolver, name)
        } else if (type == Long) {
            return Settings.Global.getLong(contentResolver, name)
        }
        return null
    } else if (group == SETTING_SECURE) {
        if (type == Int) {
            return Settings.Secure.getInt(contentResolver, name)
        } else if (type == String) {
            return Settings.Secure.getString(contentResolver, name)
        } else if (type == Float || type == Double) {
            return Settings.Secure.getFloat(contentResolver, name)
        } else if (type == Long) {
            return Settings.Secure.getLong(contentResolver, name)
        }
        return null
    } else if (group == SETTING_SYSTEM) {
        if (type == Int) {
            return Settings.System.getInt(contentResolver, name)
        } else if (type == String) {
            return Settings.System.getString(contentResolver, name)
        } else if (type == Float || type == Double) {
            return Settings.System.getFloat(contentResolver, name)
        } else if (type == Long) {
            return Settings.System.getLong(contentResolver, name)
        }
        return null
    }

    return null
}

fun MainActivity.setSetting(group: Int, name: String, value: Any) {
    if (group == SETTING_GLOBAL) {
        if (value is Int) {
            Settings.Global.putInt(contentResolver, name, value)
        } else if (value is String) {
            Settings.Global.putString(contentResolver, name, value)
        } else if (value is Float || value is Double) {
            Settings.Global.putFloat(contentResolver, name, value.toString().toFloat())
        } else if (value is Long) {
            Settings.Global.putLong(contentResolver, name, value)
        }
    } else if (group == SETTING_SECURE) {
        if (value is Int) {
            Settings.Secure.putInt(contentResolver, name, value)
        } else if (value is String) {
            Settings.Secure.putString(contentResolver, name, value)
        } else if (value is Float || value is Double) {
            Settings.Secure.putFloat(contentResolver, name, value.toString().toFloat())
        } else if (value is Long) {
            Settings.Secure.putLong(contentResolver, name, value)
        }
    } else if (group == SETTING_SYSTEM) {
        if (value is Int) {
            Settings.System.putInt(contentResolver, name, value)
        } else if (value is String) {
            Settings.System.putString(contentResolver, name, value)
        } else if (value is Float || value is Double) {
            Settings.System.putFloat(contentResolver, name, value.toString().toFloat())
        } else if (value is Long) {
            Settings.System.putLong(contentResolver, name, value)
        }
    }
}

fun MainActivity.immersiveModeReset() {
    setSetting(SETTING_GLOBAL, "policy_control", "immersive.none=*")
}

fun MainActivity.immersiveModeNav() {
    setSetting(SETTING_GLOBAL, "policy_control", "immersive.navigation=*")
}

fun MainActivity.immersiveModeStatus() {
    setSetting(SETTING_GLOBAL, "policy_control", "immersive.status=*")
}

fun MainActivity.navReset() {
    setSetting(SETTING_SECURE, "sysui_nav_bar", "space,recent;home;back,space")
    val reset = AlertDialog.Builder(this)
    reset.setTitle("Reset")
    reset.setMessage("If you find some buttons are gone from your nav, either go to Settings -> Display -> Navigation Bar and reset your order preference, or if that doesn't work run this from ADB: settings delete secure sysui_nav_bar")
    reset.setPositiveButton("Ok", null)
    reset.show()
}

fun MainActivity.navWide() {
    var default = "recent;home;back"
    val current = getSetting(SETTING_SECURE, "sysui_nav_bar", String)
    if ("recent;home;back" in current.toString() && current != null) {
        default = "recent;home;back"
    } else if ("back;home;recent" in current.toString()  && current != null) {
        default = "back;home;recent"
    }
    setSetting(SETTING_SECURE, "sysui_nav_bar", default)
}

fun MainActivity.navNarrow() {
    var default = "recent;home;back"
    val current = getSetting(SETTING_SECURE, "sysui_nav_bar", String)
    if ("recent;home;back" in current.toString()) {
        default = "recent;home;back"
    } else if ("back;home;recent" in current.toString()) {
        default = "back;home;recent"
    }
    setSetting(SETTING_SECURE, "sysui_nav_bar", "space,space,$default,space,space")
}

