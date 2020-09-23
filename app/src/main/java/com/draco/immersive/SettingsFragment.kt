package com.draco.immersive

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

class SettingsFragment() : PreferenceFragmentCompat() {
    /* Setup our preference screen */
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)

        /* Update the version code string */
        val version = findPreference<Preference>(getString(R.string.pref_version))
        val flavor = if (BuildConfig.DEBUG) "debug" else "release"
        version!!.summary = "${BuildConfig.VERSION_NAME}-${flavor}"
    }

    /* Process preference clicks */
    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val suffix = getSuffix(requireContext(), sharedPrefs)

        if (preference != null) when (preference.key) {
            getString(R.string.pref_reset) -> {
                Settings.Global.putString(requireContext().contentResolver, "policy_control", "immersive.none=*")
            }

            getString(R.string.pref_status) -> {
                Settings.Global.putString(requireContext().contentResolver, "policy_control", "immersive.status=*$suffix")
            }

            getString(R.string.pref_navigation)-> {
                Settings.Global.putString(requireContext().contentResolver, "policy_control", "immersive.navigation=*$suffix")
            }

            getString(R.string.pref_full) -> {
                Settings.Global.putString(requireContext().contentResolver, "policy_control", "immersive.full=*$suffix")
            }

            getString(R.string.pref_blocklist) -> {
                val intent = Intent(requireContext(), BlocklistActivity::class.java)
                startActivity(intent)
            }

            getString(R.string.pref_developer) -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Tyler+Nijmeh"))
                try {
                    startActivity(intent)
                } catch(_: Exception) {}
            }

            getString(R.string.pref_source_code) -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/tytydraco/Immersive"))
                startActivity(intent)
            }

            getString(R.string.pref_version) -> {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
                try {
                    startActivity(intent)
                } catch(_: Exception) {}
            }

            getString(R.string.pref_contact) -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("mailto:tylernij@gmail.com"))
                try {
                    startActivity(intent)
                } catch(_: Exception) {}
            }

            /* If we couldn't handle a preference click */
            else -> return super.onPreferenceTreeClick(preference)
        }

        return true
    }
}