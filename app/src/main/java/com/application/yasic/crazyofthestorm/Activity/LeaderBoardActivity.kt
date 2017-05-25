package com.application.yasic.crazyofthestorm.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.LeaderBoardSubAdapter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_leader_board.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.util.*
import kotlin.comparisons.compareBy

class LeaderBoardActivity() : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val titleMap = mapOf<String, String>(
                Pair("sthp", resources.getString(R.string.sthp)),
                Pair("stat", resources.getString(R.string.stat)),
                Pair("stsi", resources.getString(R.string.stsi)),
                Pair("tehp", resources.getString(R.string.tehp)),
                Pair("teat", resources.getString(R.string.teat)),
                Pair("tesi", resources.getString(R.string.tesi)),
                Pair("atfre", resources.getString(R.string.atfre)),
                Pair("atra", resources.getString(R.string.atra)),
                Pair("popular", resources.getString(R.string.popular))
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        val bundle = intent.extras
        val tag = bundle.getString("tag")
        toolbar.title = titleMap.get(tag) + "排行榜"
        setSupportActionBar(toolbar)

        doAsync {
            var jsonArray = JsonArray()
            if (tag == "popular") {
                jsonArray = getPopularJsonArray()
            } else {
                jsonArray = NetworkModel().getLeaderBoardAsJson(tag)
            }
            uiThread {
                view_loading.visibility = View.GONE
                rv_leader_board_sub_list.layoutManager = LinearLayoutManager(this@LeaderBoardActivity)
                rv_leader_board_sub_list.adapter = LeaderBoardSubAdapter(jsonArray, object : LeaderBoardSubAdapter.OnItemClickListener {
                    override fun invoke(heroId: String, heroName: String) {
                        val intent = Intent(this@LeaderBoardActivity, HeroDisplayActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("heroId", heroId)
                        bundle.putString("heroName", heroName)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                }, this@LeaderBoardActivity)
            }
        }
    }

    private fun getPopularJsonArray(): JsonArray {
        val jsonObject = NetworkModel().getPopularBoardAsJson()
        val heroList = NetworkModel().getHeroList()
        var totalScore = 0.0f
        var jsonArray = JsonArray()
        var heroListOfMap = ArrayList<Pair<String, Float>>()
        jsonObject.entrySet().forEach {
            totalScore += it.value.asFloat
        }
        heroList.forEach {
            if (jsonObject.has(it.id)) {
                heroListOfMap.add(Pair(it.id, (jsonObject.get(it.id).asFloat / totalScore)))
            }
        }
        val compare: Comparator<Pair<String, Float>> = Comparator { t1, t2 -> (t2.second * 1000 - t1.second * 1000).toInt() }
        val list = heroListOfMap.sortedWith(compare)
        list.forEach {

            var hero = JsonObject()
            hero.add("heroId", Gson().fromJson(it.first, JsonElement::class.java))
            hero.add("heroName", Gson().fromJson(getHeroName(heroList, it.first), JsonElement::class.java))
            hero.add("value", Gson().fromJson(String.format("%.2f", (it.second * 100)) + "%", JsonElement::class.java))
            jsonArray.add(hero)
        }
        return jsonArray
    }

    private fun getHeroName(heroList: MutableList<SimpleHeroItem>, id: String): String{
        var name = ""
        heroList.forEach{
            if (it.id == id){
                name = it.name
                return name
            }
        }
        return name
    }
}


