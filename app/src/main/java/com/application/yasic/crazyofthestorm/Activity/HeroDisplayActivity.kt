package com.application.yasic.crazyofthestorm.Activity

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.application.yasic.crazyofthestorm.Fragment.HeroInfoFragment
import com.application.yasic.crazyofthestorm.Fragment.HeroTalentTreeFragment
import com.application.yasic.crazyofthestorm.Fragment.LeaderBoardFragment
import com.application.yasic.crazyofthestorm.Model.HeroDataModel
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.Util.ViewPagerAdapter
import com.application.yasic.crazyofthestorm.R
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_hero_display.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class HeroDisplayActivity : AppCompatActivity(){
    private var heroId = ""
    private var heroName = ""
    private var heroJson = JsonObject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hero_display)
        val bundle = intent.extras
        heroId = bundle.getString("heroId")
        heroName = bundle.getString("heroName")
        toolbar.title = resources.getString(R.string.hero_detail_information)
        setSupportActionBar(toolbar)

        doAsync {
            heroJson = NetworkModel().getHeroInformationAsJson(heroId)
            uiThread {
                view_loading.visibility = View.GONE
                val tabTitleList = mutableListOf<String>()
                tabTitleList.add(heroName)
                tabTitleList.add(resources.getString(R.string.talent_tree_fragment_title))
                val fragmentList = mutableListOf<Fragment>()
                fragmentList.add(HeroInfoFragment())
                fragmentList.add(HeroTalentTreeFragment())
                vp_hero_display.adapter = ViewPagerAdapter(supportFragmentManager, tabTitleList, fragmentList)
                vp_hero_display.offscreenPageLimit = 2
                tl_hero_display_bottomBar.setupWithViewPager(vp_hero_display)
            }
        }
    }

    fun getHeroId(): String{
        return heroId
    }

    fun getHeroName(): String{
        return heroName
    }

    fun getHeroJson(): JsonObject{
        return heroJson
    }
}
