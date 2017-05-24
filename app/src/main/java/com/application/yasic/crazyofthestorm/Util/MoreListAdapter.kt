package com.application.yasic.crazyofthestorm.Util

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.application.yasic.crazyofthestorm.R
import kotlinx.android.synthetic.main.more_list_item.*
import org.jetbrains.anko.find

class MoreListAdapter(val items: Array<String>, val itemClick: OnItemClickListener): RecyclerView.Adapter<MoreListAdapter.ViewHolder>(){
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.more_list_item, parent, false), itemClick)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(val view: View, val itemClick: OnItemClickListener): RecyclerView.ViewHolder(view){
        private val titleView: TextView
        private val liItemView: LinearLayout

        init {
            titleView = view.find(R.id.tv_more_list_title)
            liItemView = view.find(R.id.li_more_list_item)
        }

        fun bindView(item: String){
            titleView.text = item
            liItemView.setOnClickListener { itemClick(item) }
        }
    }

    interface OnItemClickListener{
        operator fun invoke(title: String){

        }
    }
}
