package com.application.yasic.crazyofthestorm.Util

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.application.yasic.crazyofthestorm.Activity.HeroDisplayActivity
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import org.jetbrains.anko.find
import java.util.*

class LeaderBoardPrimaryAdapter(val items: Array<String>, val itemClick: OnItemClickListener): RecyclerView.Adapter<LeaderBoardPrimaryAdapter.ViewHolder>(){
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.leader_board_list_item, parent, false), itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position], position)
    }

    class ViewHolder(val view: View, val itemClick: OnItemClickListener): RecyclerView.ViewHolder(view){
        private val typeArray = arrayOf("sthp", "stat", "stsi", "tehp", "teat", "tesi", "atfre", "atra")
        private val titleView: TextView
        private val liItemView: LinearLayout

        init {
            titleView = view.find(R.id.tv_leader_board_title)
            liItemView = view.find(R.id.li_leader_board_item)
        }
        fun bindView(item: String, position: Int){
            titleView.text = item
            liItemView.setOnClickListener {
                itemClick(typeArray[position])
            }
        }
    }

    interface OnItemClickListener{
        operator fun invoke(tag: String)
    }
}
