package com.application.yasic.crazyofthestorm.Fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.yasic.crazyofthestorm.Activity.HeroDisplayActivity
import com.application.yasic.crazyofthestorm.Model.HeroDataModel
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.HeroTalentAdapter
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.fragment_hero_talent.*

class HeroTalentTreeFragment() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_hero_talent, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val heroId = (activity as HeroDisplayActivity).getHeroId()
        val heroJson = (activity as HeroDisplayActivity).getHeroJson()
        val talents = mutableListOf<JsonObject>()
        val primaryTalents = HeroDataModel().getPrimaryTalent(heroJson)
        tv_primary_text.text = primaryTalents.replace(",", " ")
        val primaryTalentArray = primaryTalents.split(",")

        val LVs = arrayOf("LV1", "LV4", "LV7", "LV10", "LV13", "LV16", "LV20")
        LVs.forEachIndexed {
            index, item ->
            run {
                val talentJson = JsonObject()
                talentJson.add("talentList", HeroDataModel().getTalent(heroJson).get(item).asJsonArray)
                talentJson.add("lv", Gson().fromJson(item, JsonElement::class.java))
                talentJson.add("heroId", Gson().fromJson(heroId, JsonElement::class.java))
                talentJson.add("select", Gson().fromJson(primaryTalentArray[index], JsonElement::class.java))
                talents.add(talentJson)
            }
        }

        rv_hero_talent.layoutManager = LinearLayoutManager(activity)
        rv_hero_talent.adapter = HeroTalentAdapter(talents)
    }
}
