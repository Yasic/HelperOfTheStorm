package com.application.yasic.crazyofthestorm.Util

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.application.yasic.crazyofthestorm.Object.HeroTrait
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find
import org.w3c.dom.Text

class HeroTraitAdapter(val items: List<HeroTrait>): RecyclerView.Adapter<HeroTraitAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hero_trait_item, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val iconView: ImageView
        private val titleView: TextView
        private val costView: TextView
        private val shortCutView: TextView
        private val cdView: TextView
        private val descriptionView: TextView

        init {
            iconView = view.find(R.id.iv_hero_trait)
            titleView = view.find(R.id.tv_hero_trait_title)
            costView = view.find(R.id.tv_cost)
            shortCutView = view.find(R.id.tv_shortcut)
            cdView = view.find(R.id.tv_cd)
            descriptionView = view.find(R.id.tv_description)
        }

        fun bind(heroTrait: HeroTrait) {
            with(heroTrait){
                Picasso.with(iconView.context).load(WannaGet.htap() + "herotrait/$heroId/$title").into(iconView)
                titleView.text = title
                costView.text = cost
                shortCutView.text = shortCut
                cdView.text = cd
                descriptionView.text = description
            }
        }

    }
}
