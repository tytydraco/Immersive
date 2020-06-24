package com.draco.immersive

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment(private val contentResolver: ContentResolver) : PreferenceFragmentCompat() {
    /* Suffix to secure setting that excludes SystemUI from immersive mode */
    private val excludeSystemUI = ",-com.android.systemui"

    /* Setup our preference screen */
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        /* Update the version code string */
        val version = findPreference<Preference>("version")
        val flavor = if (BuildConfig.DEBUG)
            "debug"
        else
            "release"
        version!!.summary = "${BuildConfig.VERSION_NAME}-${flavor}"
    }

    /* Process preference clicks */
    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        val suffix = if (findPreference<CheckBoxPreference>("exclude_systemui")!!.isChecked)
            excludeSystemUI
        else
            ""

        if (preference != null) when (preference.key) {
            "reset" -> {
                Settings.Global.putString(contentResolver, "policy_control", "immersive.none=*")
            }

            "status" -> {
                Settings.Global.putString(contentResolver, "policy_control", "immersive.navigation=*$suffix")
            }

            "navigation" -> {
                Settings.Global.putString(contentResolver, "policy_control", "immersive.status=*$suffix")
            }

            "full" -> {
                Settings.Global.putString(contentResolver, "policy_control", "immersive.full=*$suffix")
            }

            "developer" -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Tyler+Nijmeh"))
                startActivity(intent)
            }

            "version" -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
                startActivity(intent)
            }

            "contact" -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:tylernij@gmail.com"))
                startActivity(intent)
            }

            /* If we couldn't handle a preference click */
            else -> return super.onPreferenceTreeClick(preference)
        }

        return true
    }
}