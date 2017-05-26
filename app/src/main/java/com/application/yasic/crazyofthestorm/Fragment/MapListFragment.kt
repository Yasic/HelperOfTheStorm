package com.application.yasic.crazyofthestorm.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.yasic.crazyofthestorm.Activity.LeaderBoardActivity
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.LeaderBoardPrimaryAdapter
import kotlinx.android.synthetic.main.fragment_map_list.*


class MapListFragment(): Fragment(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_map_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_map_list.layoutManager = LinearLayoutManager(activity)
        val titleList = arrayOf("1", "2", "3"
        )
        rv_map_list.adapter = LeaderBoardPrimaryAdapter(titleList, object: LeaderBoardPrimaryAdapter.OnItemClickListener{
            override fun invoke(tag: String) {

            }
        })
    }
}
