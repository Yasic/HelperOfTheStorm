package com.application.yasic.crazyofthestorm.Activity

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.LeaderBoardSubAdapter
import kotlinx.android.synthetic.main.activity_leader_board.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class LeaderBoardActivity(): AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        val titleMap = mapOf<String, String>(
                Pair("sthp", resources.getString(R.string.sthp)),
                Pair("stat", resources.getString(R.string.stat)),
                Pair("stsi", resources.getString(R.string.stsi)),
                Pair("tehp", resources.getString(R.string.tehp)),
                Pair("teat", resources.getString(R.string.teat)),
                Pair("tesi", resources.getString(R.string.tesi)),
                Pair("atfre", resources.getString(R.string.atfre)),
                Pair("atra", resources.getString(R.string.atra))
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leader_board)
        val bundle = intent.extras
        val tag = bundle.getString("tag")
        toolbar.title = titleMap.get(tag) + "排行榜"
        setSupportActionBar(toolbar)

        doAsync {
            val jsonArray = NetworkModel().getLeaderBoardAsJson(tag)
            uiThread {
                view_loading.visibility = View.GONE
                rv_leader_board_sub_list.layoutManager = LinearLayoutManager(this@LeaderBoardActivity)
                rv_leader_board_sub_list.adapter = LeaderBoardSubAdapter(jsonArray, object: LeaderBoardSubAdapter.OnItemClickListener {
                    override fun invoke(heroId: String, heroName: String) {
                        val intent = Intent(this@LeaderBoardActivity, HeroDisplayActivity::class.java)
                        val bundle = Bundle()
                        bundle.putString("heroId", heroId)
                        bundle.putString("heroName", heroName)
                        intent.putExtras(bundle)
                        startActivity(intent)
                    }
                })
            }
        }
    }
}


