package com.application.yasic.crazyofthestorm.Fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.application.yasic.crazyofthestorm.Activity.AboutActivity
import com.application.yasic.crazyofthestorm.Activity.FeedBackActivity
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.MoreListAdapter
import kotlinx.android.synthetic.main.fragment_more.*


class MoreListFragment() : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_more_list.layoutManager = LinearLayoutManager(activity)
        val titleList = arrayOf(
                activity.resources.getString(R.string.feedback_activity_title),
                activity.resources.getString(R.string.about_activity_title)
        )
        rv_more_list.adapter = MoreListAdapter(titleList, object : MoreListAdapter.OnItemClickListener {
            override fun invoke(title: String) {
                when (title) {
                    activity.resources.getString(R.string.feedback_activity_title)-> {
                        val intent = Intent(activity, FeedBackActivity::class.java)
                        startActivity(intent)
                    }
                    activity.resources.getString(R.string.about_activity_title) -> {
                        val intent = Intent(activity, AboutActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        })
    }
}


