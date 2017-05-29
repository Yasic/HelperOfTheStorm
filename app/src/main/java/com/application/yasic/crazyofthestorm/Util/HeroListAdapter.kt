package com.application.yasic.crazyofthestorm.Util

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.w3c.dom.Text

class HeroListAdapter(val items: List<SimpleHeroItem>, val	itemClick: OnItemClickListener, var context: Context): RecyclerView.Adapter<HeroListAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hero_list_item, parent, false), itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindSimpleHero(items[position], context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View, val itemClick: OnItemClickListener): RecyclerView.ViewHolder(view){
        private val iconView: ImageView
        private val nameView: TextView
        private val roleView: TextView

        init {
            nameView = view.find(R.id.hero_name)
            roleView = view.find(R.id.hero_role)
            iconView = view.find(R.id.hero_icon)
        }

        fun bindSimpleHero(simpleHeroItem: SimpleHeroItem, context: Context){
            with(simpleHeroItem){
                NetworkModel().loadImage(iconView, WannaGet.htap() + "heroicon/" + id)
                nameView.text = name
                when(role[0].toString() + role[1].toString()){
                    "wa" -> roleView.text = context.resources.getString(R.string.warrior)
                    "sp" -> roleView.text = context.resources.getString(R.string.specialist)
                    "su" -> roleView.text = context.resources.getString(R.string.support)
                    "as" -> roleView.text = context.resources.getString(R.string.assassin)
                    else -> roleView.text = context.resources.getString(R.string.unknown_role)
                }
                itemView.setOnClickListener { itemClick(simpleHeroItem) }
            }
        }
    }

    interface OnItemClickListener{
        operator fun invoke(simpleHeroItem: SimpleHeroItem)
    }
}
