package com.draco.immersive.tiles

import android.provider.Settings
import android.service.quicksettings.TileService
import com.draco.immersive.hasPermissions

class ResetTileService: TileService() {
    override fun onClick() {
        super.onClick()
        if (!hasPermissions(this)) return
        Settings.Global.putString(contentResolver, "policy_control", "immersive.none=*")
    }
}