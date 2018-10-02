package com.draco.immersive

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.preference.PreferenceFragmentCompat
import android.widget.ListView

class OptionFragment : PreferenceFragmentCompat() {

    var callback: (() -> Unit)? = null

    lateinit var reset: () -> Unit
    lateinit var status: () -> Unit
    lateinit var nav: () -> Unit
    lateinit var full: () -> Unit
    lateinit var developer: () -> Unit
    lateinit var version: () -> Unit
    lateinit var contact: () -> Unit

    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.options)

        with (preferenceManager) {
            findPreference("reset").icon = ContextCompat.getDrawable(context, R.drawable.ic_clear)
            findPreference("reset").setOnPreferenceClickListener {
                reset()
                return@setOnPreferenceClickListener true
            }

            findPreference("status").icon = ContextCompat.getDrawable(context, R.drawable.ic_vertical_align_top)
            findPreference("status").setOnPreferenceClickListener {
                status()
                return@setOnPreferenceClickListener true
            }

            findPreference("nav").icon = ContextCompat.getDrawable(context, R.drawable.ic_vertical_align_bottom)
            findPreference("nav").setOnPreferenceClickListener {
                nav()
                return@setOnPreferenceClickListener true
            }

            findPreference("full").icon = ContextCompat.getDrawable(context, R.drawable.ic_vertical_align_center)
            findPreference("full").setOnPreferenceClickListener {
                full()
                return@setOnPreferenceClickListener true
            }

            findPreference("exclude_systemui").icon = ContextCompat.getDrawable(context, R.drawable.ic_bug_report)
            findPreference("full_hide").icon = ContextCompat.getDrawable(context, R.drawable.ic_fullscreen)

            findPreference("developer").icon = ContextCompat.getDrawable(context, R.drawable.ic_person)
            findPreference("developer").setOnPreferenceClickListener {
                developer()
                return@setOnPreferenceClickListener true
            }

            findPreference("version").icon = ContextCompat.getDrawable(context, R.drawable.ic_developer_board)
            findPreference("version").summary = "${BuildConfig.VERSION_NAME}-${if (BuildConfig.DEBUG) "debug" else "release"} (${BuildConfig.VERSION_CODE})"
            findPreference("version").setOnPreferenceClickListener {
                version()
                return@setOnPreferenceClickListener true
            }

            findPreference("contact").icon = ContextCompat.getDrawable(context, R.drawable.ic_email)
            findPreference("contact").setOnPreferenceClickListener {
                contact()
                return@setOnPreferenceClickListener true
            }
        }

        if (callback != null)
            callback?.invoke()
    }
}