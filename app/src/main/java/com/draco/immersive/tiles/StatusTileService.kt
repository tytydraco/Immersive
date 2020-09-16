package com.draco.immersive.tiles

import android.provider.Settings
import android.service.quicksettings.TileService
import androidx.preference.PreferenceManager
import com.draco.immersive.getSuffix
import com.draco.immersive.hasPermissions

class StatusTileService: TileService() {
    override fun onClick() {
        super.onClick()
        if (!hasPermissions(this)) return
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val suffix = getSuffix(this, sharedPrefs)
        Settings.Global.putString(contentResolver, "policy_control", "immersive.status=*$suffix")
    }
}