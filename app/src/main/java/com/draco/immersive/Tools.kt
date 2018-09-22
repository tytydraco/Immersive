package com.draco.immersive

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.provider.Settings
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.widget.Toast
import java.io.BufferedReader
import java.io.InputStreamReader

const val excludeSystemUI = ",-com.android.systemui"

fun asyncExec(func: () -> Unit) {
    object : AsyncTask<Unit, Unit, Unit>() {
        override fun doInBackground(vararg params: Unit?) = func()
    }.execute()
}

fun isRoot(): Boolean {
    return run("ps", null, true).contains("root")
}

fun run(cmd: String, callback: ((String) -> Unit)?, asRoot: Boolean = false): String {
    try {
        val command = if (asRoot) "su -c $cmd" else cmd
        val process = Runtime.getRuntime().exec(command)
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val output = StringBuffer()
        do {
            val line = reader.readLine() ?: break
            if (output.toString() != "") {
                output.append("\n" + line)
            } else {
                output.append(line)
            }
        } while (true)
        reader.close()
        process.waitFor()
        val outputString = output.toString()
        if (callback != null)
            callback(outputString)
        return outputString
    } catch (e: Exception) {
        return ""
    }
}

fun permissionCheck(context: Context, callback: (() -> Unit)? = null) {
    val permissionCheck = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_SECURE_SETTINGS)
    if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
        if (callback != null) callback()
    } else {
        val permCommand = "pm grant ${BuildConfig.APPLICATION_ID} android.permission.WRITE_SECURE_SETTINGS"
        val error = AlertDialog.Builder(context)
        error.setTitle("Missing Permissions")
        error.setMessage("To allow this app to work, you must run an ADB command via your computer.\n\nadb shell $permCommand")
        error.setPositiveButton("Try Again") { _, _ -> permissionCheck(context) }
        error.setNeutralButton("Use Root") { _, _ ->
            if (isRoot()) {
                asyncExec { run(permCommand, null, true) }
                if (callback != null) callback()
            } else {
                Toast.makeText(context, "Root check failed. Make sure Immersive has root permissions.", Toast.LENGTH_SHORT).show()
                permissionCheck(context)
            }
        }
        error.setNegativeButton("Close") { _, _ -> System.exit(0) }
        error.setCancelable(false)
        error.show()
    }
}

fun MainActivity.immersiveModeReset() {
    Settings.Global.putString(contentResolver, "policy_control", "immersive.none=*")
}

fun MainActivity.immersiveModeNav(exclude: Boolean) {
    Settings.Global.putString(contentResolver, "policy_control", "immersive.navigation=*${if (exclude) excludeSystemUI else ""}")
}

fun MainActivity.immersiveModeStatus(exclude: Boolean) {
    Settings.Global.putString(contentResolver, "policy_control", "immersive.status=*${if (exclude) excludeSystemUI else ""}")
}

fun MainActivity.immersiveModeFull(exclude: Boolean) {
    Settings.Global.putString(contentResolver, "policy_control", "immersive.full=*${if (exclude) excludeSystemUI else ""}")
}


