package com.draco.immersive.tiles

import android.provider.Settings
import android.service.quicksettings.TileService
import androidx.preference.PreferenceManager
import com.draco.immersive.getSuffix

class FullTileService: TileService() {
    override fun onClick() {
        super.onClick()
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val suffix = getSuffix(this, sharedPrefs)
        Settings.Global.putString(contentResolver, "policy_control", "immersive.full=*$suffix")
    }
}