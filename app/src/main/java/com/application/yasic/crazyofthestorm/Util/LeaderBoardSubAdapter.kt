package com.application.yasic.crazyofthestorm.Util

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.jetbrains.anko.find


class LeaderBoardSubAdapter(val items: JsonArray, val itemClick: OnItemClickListener, val context: Context): RecyclerView.Adapter<LeaderBoardSubAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items.get(position).asJsonObject, position, context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.leader_board_item, parent, false), itemClick)
    }

    override fun getItemCount(): Int {
        return items.size()
    }

    class ViewHolder(val view: View, val itemClick: OnItemClickListener): RecyclerView.ViewHolder(view){
        private val index: TextView
        private val heroName: TextView
        private val value: TextView
        private val liItem: LinearLayout

        init {
            index = view.find(R.id.tv_index)
            heroName = view.find(R.id.tv_hero_name)
            value = view.find(R.id.tv_value)
            liItem = view.find(R.id.li_leader_board_item)
        }

        fun bindView(item: JsonObject, position: Int, context: Context){
            if (position < 3){
                index.setTextColor(context.resources.getColor(R.color.colorAccent))
                heroName.setTextColor(context.resources.getColor(R.color.colorAccent))
                value.setTextColor(context.resources.getColor(R.color.colorAccent))
            }else{
                index.setTextColor(context.resources.getColor(R.color.colorAccentWhite))
                heroName.setTextColor(context.resources.getColor(R.color.colorAccentWhite))
                value.setTextColor(context.resources.getColor(R.color.colorAccentWhite))
            }
            index.text = (position+1).toString()
            heroName.text = item.get("heroName").asString
            liItem.setOnClickListener { itemClick(item.get("heroId").asString, item.get("heroName").asString) }
            value.text = item.get("value").asString
        }
    }

    interface OnItemClickListener{
        operator fun invoke(heroId: String, heroName: String){}
    }

}