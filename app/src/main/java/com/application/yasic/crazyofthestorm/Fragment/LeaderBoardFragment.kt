package com.application.yasic.crazyofthestorm.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.yasic.crazyofthestorm.Activity.HeroDisplayActivity
import com.application.yasic.crazyofthestorm.Activity.LeaderBoardActivity
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.Util.HeroListAdapter
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.LeaderBoardPrimaryAdapter
import kotlinx.android.synthetic.main.fragment_hero_list.*
import kotlinx.android.synthetic.main.fragment_leader_board.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class LeaderBoardFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_leader_board, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_leader_board.layoutManager = LinearLayoutManager(activity)
        val titleList = arrayOf(activity.resources.getString(R.string.sthp),
                activity.resources.getString(R.string.stat),
                activity.resources.getString(R.string.stsi),
                activity.resources.getString(R.string.tehp),
                activity.resources.getString(R.string.teat),
                activity.resources.getString(R.string.tesi),
                activity.resources.getString(R.string.atfre),
                activity.resources.getString(R.string.atra)
                )
        rv_leader_board.adapter = LeaderBoardPrimaryAdapter(titleList, object: LeaderBoardPrimaryAdapter.OnItemClickListener{
            override fun invoke(tag: String) {
                val intent = Intent(activity, LeaderBoardActivity::class.java)
                val bundle = Bundle()
                bundle.putString("tag", tag)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        })

    }


}

