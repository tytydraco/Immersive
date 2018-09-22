package com.draco.immersive

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.PreferenceManager
import android.provider.Settings
import android.widget.Button


class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        if (p1 == "exclude_systemui") {
            if (p0 != null)
                excludeSystemUI = p0.getBoolean("exclude_systemui", false)
        }
    }

    private lateinit var optFrag: OptionFragment
    private lateinit var prefs: SharedPreferences
    private var excludeSystemUI = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = PreferenceManager.getDefaultSharedPreferences(baseContext)
        prefs.registerOnSharedPreferenceChangeListener(this)

        // could end up with overlapping fragments
        if (savedInstanceState == null) {
            optFrag = OptionFragment()
            optFrag.retainInstance = true

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.optContainer, optFrag)
                    .commit()
        }

        // check for permission before proceeding
        permissionCheck(this) {
            setupFragmentFunctions()
        }
    }

    private fun setupFragmentFunctions() {
        optFrag.reset = { immersiveModeReset() }
        optFrag.status = { immersiveModeStatus(excludeSystemUI) }
        optFrag.nav = { immersiveModeNav(excludeSystemUI) }
        optFrag.full = { immersiveModeFull(excludeSystemUI) }
        optFrag.contact = {
            try {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("mailto:tylernij@gmail.com")))
            } catch (_: Exception) {}
        }
        optFrag.developer = {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Tyler+Nijmeh")))
        }
        optFrag.version = {
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
                startActivity(intent)
            } catch (e: ActivityNotFoundException) {
                val intent = Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS)
                startActivity(intent)
            }
        }
    }
}
