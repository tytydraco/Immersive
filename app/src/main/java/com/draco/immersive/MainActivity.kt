package com.draco.immersive

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.concurrent.fixedRateTimer

class MainActivity : AppCompatActivity() {
    private val adbCommand = "pm grant ${BuildConfig.APPLICATION_ID} android.permission.WRITE_SECURE_SETTINGS"
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dialog = AlertDialog.Builder(this)
                .setTitle("Missing Permissions")
                .setMessage(getString(R.string.adb_tutorial) + "adb shell $adbCommand")
                .setPositiveButton("Check Again", null)
                .setNeutralButton("Setup ADB", null)
                .setNegativeButton("Use Root", null)
                .setCancelable(false)
                .create()

        /* Setup settings page */
        val settingsFragment = SettingsFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, settingsFragment)
            .commit()

        /* Request permissions */
        checkPermissions()
    }

    private fun checkPermissions() {
        if (hasPermissions(this))
            return

        dialog.setOnShowListener {
            /* We don't dismiss on Check Again unless we actually have the permission */
            val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
            positiveButton.setOnClickListener {
                if (hasPermissions(this))
                    dialog.dismiss()
            }

            /* Open tutorial but do not dismiss until user presses Check Again */
            val neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL)
            neutralButton.setOnClickListener {
                val uri = Uri.parse("https://www.xda-developers.com/install-adb-windows-macos-linux/")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }

            /* Try using root permissions */
            val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)
            negativeButton.setOnClickListener {
                try {
                    ProcessBuilder("su", "-c", adbCommand).start()
                    if (hasPermissions(this))
                        dialog.dismiss()
                } catch (_: Exception) {}
            }
        }

        dialog.show()
    }
}
