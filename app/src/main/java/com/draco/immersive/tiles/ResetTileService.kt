package com.draco.immersive.tiles

import android.provider.Settings
import android.service.quicksettings.TileService

class ResetTileService: TileService() {
    override fun onClick() {
        super.onClick()
        Settings.Global.putString(contentResolver, "policy_control", "immersive.none=*")
    }
}