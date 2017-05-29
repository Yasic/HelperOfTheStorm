package com.application.yasic.crazyofthestorm.Fragment


import android.content.ReceiverCallNotAllowedException
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.application.yasic.crazyofthestorm.Activity.HeroDisplayActivity
import com.application.yasic.crazyofthestorm.Model.HeroDataModel
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.Object.HeroTrait
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.HeroInfoAdapter
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_hero_info.*
import kotlinx.android.synthetic.main.header_text_item.*
import kotlinx.android.synthetic.main.layout_hero_property.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import java.util.*

class HeroInfoFragment() : Fragment() {
    var heroId: String = ""
    var heroName: String = ""
    var heroInfoReady = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_hero_info, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        heroId = (activity as HeroDisplayActivity).getHeroId()
        heroName = (activity as HeroDisplayActivity).getHeroName()
        val heroJson = (activity as HeroDisplayActivity).getHeroJson()
        heroInfoReady = true

        val heroInfoList = mutableListOf<JsonObject>()

        val headerTextJson = heroJson
        headerTextJson.add("itemViewType", Gson().fromJson(HeroInfoAdapter.HEADERTEXT.toString(), JsonElement::class.java))
        heroInfoList.add(headerTextJson)


        for (item in HeroDataModel().getHeroTrait(heroJson)) {
            val traitItem: JsonObject = item as JsonObject
            traitItem.add("itemViewType", Gson().fromJson(HeroInfoAdapter.TRAIT.toString(), JsonElement::class.java))
            traitItem.add("id", Gson().fromJson(heroId, JsonElement::class.java))
            heroInfoList.add(traitItem)
        }

        val startingAbilityText = JsonObject()
        startingAbilityText.add("itemViewType", Gson().fromJson(HeroInfoAdapter.STARTINGABILITYTEXT.toString(), JsonElement::class.java))
        heroInfoList.add(startingAbilityText)

        for (item in HeroDataModel().getHeroStartingAbility(heroJson)) {
            (item as JsonObject).add("itemViewType", Gson().fromJson(HeroInfoAdapter.STARTINGABILITY.toString(), JsonElement::class.java))
            item.add("id", Gson().fromJson(heroId, JsonElement::class.java))
            heroInfoList.add(item)
        }

        val heroicAbilityText = JsonObject()
        heroicAbilityText.add("itemViewType", Gson().fromJson(HeroInfoAdapter.HORICABILITYTEXT.toString(), JsonElement::class.java))
        heroInfoList.add(heroicAbilityText)

        for (item in HeroDataModel().getHeroicAbility(heroJson)) {
            (item as JsonObject).add("itemViewType", Gson().fromJson(HeroInfoAdapter.HORICABILITY.toString(), JsonElement::class.java))
            item.add("id", Gson().fromJson(heroId, JsonElement::class.java))
            heroInfoList.add(item)
        }

        rv_hero_info.layoutManager = LinearLayoutManager(activity)
        rv_hero_info.adapter = HeroInfoAdapter(heroInfoList)
    }
}
