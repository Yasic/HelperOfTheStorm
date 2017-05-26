package com.application.yasic.crazyofthestorm.Util

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.application.yasic.crazyofthestorm.R
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jetbrains.anko.find

class MapListAdapter(val items: JsonArray, val context: Context, val itemClick: MapListAdapter.OnItemClickListener):RecyclerView.Adapter<MapListAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position].asJsonObject)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.map_list_item, parent), itemClick)
    }

    override fun getItemCount(): Int {
        return items.size()
    }

    class ViewHolder(val view: View, val itemClick: OnItemClickListener):RecyclerView.ViewHolder(view){
        private val mapListItem: TextView
        private val liItemView: LinearLayout

        init {
            mapListItem = view.find(R.id.tv_map_list_title)
            liItemView = view.find(R.id.li_map_list_item)
        }

        fun bindView(item: JsonObject){
            mapListItem.text = item.get("name").asString
            liItemView.setOnClickListener { itemClick(item.get("id").asString) }
        }
    }

    interface OnItemClickListener{
        operator fun invoke(title: String){}
    }
}
