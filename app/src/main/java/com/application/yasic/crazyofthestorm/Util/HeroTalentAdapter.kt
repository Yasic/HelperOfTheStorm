package com.application.yasic.crazyofthestorm.Util

import android.media.Image
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.application.yasic.crazyofthestorm.R
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.talent_tree_item.*
import org.jetbrains.anko.find
import org.w3c.dom.Text
import java.util.*

class HeroTalentAdapter(val items: List<JsonObject>) : RecyclerView.Adapter<HeroTalentAdapter.TalentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TalentViewHolder? {
        return TalentViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.talent_tree_item, parent, false))
    }

    override fun onBindViewHolder(holder: HeroTalentAdapter.TalentViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class TalentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val lvText: TextView
        private val liTalent0: LinearLayout
        private val liTalent1: LinearLayout
        private val liTalent2: LinearLayout
        private val liTalent3: LinearLayout
        private val liTalent4: LinearLayout
        private val ivTalent0: ImageView
        private val ivTalent1: ImageView
        private val ivTalent2: ImageView
        private val ivTalent3: ImageView
        private val ivTalent4: ImageView
        private val talentTitle: TextView
        private val talentType: TextView
        private val talentCD: TextView
        private val talentDescription: TextView
        private var talentViewArray: Array<ImageView>
        private var talentLinearViewArray: Array<LinearLayout>

        init {
            lvText = view.find(R.id.tv_lv_text)
            liTalent0 = view.find(R.id.li_talent_0)
            liTalent1 = view.find(R.id.li_talent_1)
            liTalent2 = view.find(R.id.li_talent_2)
            liTalent3 = view.find(R.id.li_talent_3)
            liTalent4 = view.find(R.id.li_talent_4)
            ivTalent0 = view.find(R.id.iv_talent_0)
            ivTalent1 = view.find(R.id.iv_talent_1)
            ivTalent2 = view.find(R.id.iv_talent_2)
            ivTalent3 = view.find(R.id.iv_talent_3)
            ivTalent4 = view.find(R.id.iv_talent_4)
            talentTitle = view.find(R.id.tv_talent_title)
            talentType = view.find(R.id.tv_talent_type)
            talentCD = view.find(R.id.tv_talent_cd)
            talentDescription = view.find(R.id.tv_talent_description)
            talentViewArray = arrayOf(ivTalent0, ivTalent1, ivTalent2, ivTalent3, ivTalent4)
            talentLinearViewArray = arrayOf(liTalent0, liTalent1, liTalent2, liTalent3, liTalent4)
        }

        fun bindView(talentJson: JsonObject) {
            val heroId = talentJson.get("heroId").asString.replace("\"", "")
            val talentLV = talentJson.get("lv").asString.replace("\"", "")
            val talentArray = talentJson.get("talentList").asJsonArray
            var select = talentJson.get("select").asInt - 1
            lvText.text = talentLV

            talentLinearViewArray.forEach{
                it.visibility = View.GONE
            }
            talentArray.forEachIndexed {
                index, item ->
                run {
                    talentLinearViewArray[index].visibility = View.VISIBLE
                    talentLinearViewArray[index].setBackgroundResource(R.drawable.white_border)
                    if (index == select){
                        talentLinearViewArray[index].setBackgroundResource(R.drawable.blue_border_highlight)
                    }
                    talentTitle.text = talentArray.get(select).asJsonObject.get("title").asString
                    talentType.text = talentArray.get(select).asJsonObject.get("active").asString
                    talentCD.text = talentArray.get(select).asJsonObject.get("talent-cd").asString
                    talentDescription.text = talentArray.get(select).asJsonObject.get("description").asString
                    Picasso.with(talentViewArray[index].context).load(WannaGet.htap() + "herotalent/$heroId/$talentLV/" + item.asJsonObject.get("title").asString).into(talentViewArray[index])

                    talentLinearViewArray[index].setOnClickListener {
                        if (select != index){
                            talentJson.remove("select")
                            talentJson.add("select", Gson().fromJson((index+1).toString(), JsonElement::class.java))
                            select = index
                            talentLinearViewArray.forEach{
                                it.setBackgroundResource(R.drawable.white_border)
                            }
                            talentLinearViewArray[select].setBackgroundResource(R.drawable.blue_border_highlight)
                            talentTitle.text = talentArray.get(select).asJsonObject.get("title").asString
                            talentType.text = talentArray.get(select).asJsonObject.get("active").asString
                            talentCD.text = talentArray.get(select).asJsonObject.get("talent-cd").asString
                            talentDescription.text = talentArray.get(select).asJsonObject.get("description").asString
                        }
                    }
                }
            }
        }
    }

}