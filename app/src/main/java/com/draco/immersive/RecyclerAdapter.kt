package com.draco.immersive

import android.content.SharedPreferences
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.checkbox.MaterialCheckBox
import kotlin.math.roundToInt

class RecyclerAdapter(
        private var appList: ArrayList<AppInfo>,
        private val resources: Resources,
        private val sharedPrefs: SharedPreferences
): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {
    private var checkedItems = arrayListOf<String>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkbox = itemView.findViewById(R.id.checkbox) as MaterialCheckBox
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)

        val savedBlocklistIds = sharedPrefs.getStringSet("blocklist_ids", emptySet())
        if (savedBlocklistIds != null) checkedItems = ArrayList(savedBlocklistIds)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return appList.size
    }

    override fun getItemId(position: Int): Long {
        return appList[position].hashCode().toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val info = appList[position]

        if (checkedItems.contains(info.id))
            holder.checkbox.isChecked = true

        holder.checkbox.text = info.name
        val icon = info.img!!
        val displayMetrics = resources.displayMetrics
        val iconSize = (resources.getDimension(R.dimen.icon_sze) / displayMetrics.density).roundToInt()
        icon.setBounds(0, 0, iconSize, iconSize)
        holder.checkbox.setCompoundDrawables(icon, null, null, null)

        holder.checkbox.setOnCheckedChangeListener { _, checked ->
            if (checked)
                checkedItems.add(info.id)
            else
                checkedItems.remove(info.id)

            val editor = sharedPrefs.edit()
            editor.putStringSet("blocklist_ids", checkedItems.toSet())
            editor.apply()
        }
    }
}