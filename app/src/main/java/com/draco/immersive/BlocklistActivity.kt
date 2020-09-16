package com.draco.immersive

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.draco.immersive.R
import java.util.*
import kotlin.collections.ArrayList

class BlocklistActivity: AppCompatActivity() {
    private fun getAppList(): ArrayList<AppInfo> {
        val launcherIntent = Intent(Intent.ACTION_MAIN, null)
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER)

        val activities = packageManager.queryIntentActivities(launcherIntent, 0)
        val appList = ArrayList<AppInfo>()
        for (app in activities) {
            val appId = app.activityInfo.packageName

            val info = AppInfo()
            with (info) {
                id = appId
                name = app.activityInfo.loadLabel(packageManager).toString()
                img = packageManager.getApplicationIcon(appId)
            }

            appList.add(info)
        }

        appList.sortBy {
            it.name.toLowerCase(Locale.getDefault())
        }

        return appList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blocklist)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val recycler = findViewById<RecyclerView>(R.id.recycler)
        val recyclerAdapter = RecyclerAdapter(getAppList(), resources, sharedPrefs)
        recyclerAdapter.setHasStableIds(true)

        with (recycler) {
            setItemViewCacheSize(1000)
            adapter = recyclerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }
}