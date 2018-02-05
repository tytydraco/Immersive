package com.draco.immersive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {

    lateinit var btnImmersiveReset:Button
    lateinit var btnImmersiveNav:Button
    lateinit var btnImmersiveStatus:Button

    lateinit var btnNavReset:Button
    lateinit var btnNavWide:Button
    lateinit var btnNavNarrow:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check for permission before proceeding
        permissionCheck()

        // setup the ids
        btnImmersiveReset = findViewById(R.id.btn_immersive_reset)
        btnImmersiveNav = findViewById(R.id.btn_immersive_nav)
        btnImmersiveStatus = findViewById(R.id.btn_immersive_status)

        btnNavReset = findViewById(R.id.btn_nav_reset)
        btnNavWide = findViewById(R.id.btn_nav_wide)
        btnNavNarrow = findViewById(R.id.btn_nav_narrow)

        // setup the buttons
        btnImmersiveReset.setOnClickListener { immersiveModeReset() }
        btnImmersiveNav.setOnClickListener { immersiveModeNav() }
        btnImmersiveStatus.setOnClickListener { immersiveModeStatus() }

        btnNavReset.setOnClickListener { navReset() }
        btnNavWide.setOnClickListener { navWide() }
        btnNavNarrow.setOnClickListener { navNarrow() }
    }
}
