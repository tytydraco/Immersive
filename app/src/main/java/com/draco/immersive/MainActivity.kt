package com.draco.immersive

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button


class MainActivity : AppCompatActivity() {

    lateinit var btnImmersiveReset:Button
    lateinit var btnImmersiveNav:Button
    lateinit var btnImmersiveStatus:Button
    lateinit var btnImmersiveFull:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // check for permission before proceeding
        permissionCheck()

        // setup the ids
        btnImmersiveReset = findViewById(R.id.btn_immersive_reset)
        btnImmersiveNav = findViewById(R.id.btn_immersive_nav)
        btnImmersiveStatus = findViewById(R.id.btn_immersive_status)
        btnImmersiveFull = findViewById(R.id.btn_immersive_full)

        // setup the buttons
        btnImmersiveReset.setOnClickListener { immersiveModeReset() }
        btnImmersiveNav.setOnClickListener { immersiveModeNav() }
        btnImmersiveStatus.setOnClickListener { immersiveModeStatus() }
        btnImmersiveFull.setOnClickListener { immersiveModeFull() }
    }
}
