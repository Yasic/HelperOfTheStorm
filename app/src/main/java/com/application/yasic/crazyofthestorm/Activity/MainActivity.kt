package com.application.yasic.crazyofthestorm.Activity


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.support.annotation.UiThread
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.SimpleAdapter
import android.widget.Toast
import com.application.yasic.crazyofthestorm.Fragment.HeroListFragment
import com.application.yasic.crazyofthestorm.Fragment.LeaderBoardFragment
import com.application.yasic.crazyofthestorm.Fragment.MoreListFragment
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.Util.HeroListAdapter
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import com.application.yasic.crazyofthestorm.Util.ViewPagerAdapter
import kotlinx.android.synthetic.main.fragment_hero_list.*
import kotlinx.android.synthetic.main.activity_main_view.*
import org.jetbrains.anko.custom.async
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {
    private var heroListJson = mutableListOf<SimpleHeroItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        setSupportActionBar(toolbar)
        doAsync {
            heroListJson = NetworkModel().getHeroList()
            uiThread {
                view_loading.visibility = View.GONE
                val tabTitleList = mutableListOf<String>()
                tabTitleList.add(resources.getString(R.string.hero_list_fragment_title))
                tabTitleList.add(resources.getString(R.string.leader_boarder_fragment_title))
                tabTitleList.add(resources.getString(R.string.more_fragment_title))
                val fragmentList = mutableListOf<Fragment>()
                fragmentList.add(HeroListFragment())
                fragmentList.add(LeaderBoardFragment())
                fragmentList.add(MoreListFragment())
                vp_MainInterface.adapter = ViewPagerAdapter(supportFragmentManager, tabTitleList, fragmentList)
                vp_MainInterface.offscreenPageLimit = 3
                tl_BottomBar.setupWithViewPager(vp_MainInterface)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false)
        }
        return false
    }

    fun getHeroListJson(): MutableList<SimpleHeroItem>{
        return heroListJson
    }
}
