package com.application.yasic.crazyofthestorm.Fragment


import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.application.yasic.crazyofthestorm.Activity.HeroDisplayActivity
import com.application.yasic.crazyofthestorm.Activity.MainActivity
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.Util.HeroListAdapter
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import kotlinx.android.synthetic.main.fragment_hero_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class HeroListFragment : Fragment() {
    private var heroListFinished = false
    private var selectRolePosition = 4
    private var selectGamePosition = 5
    private var showHeroList: MutableList<SimpleHeroItem> = mutableListOf()
    private var heroList: MutableList<SimpleHeroItem> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_hero_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHeroListView()
        setFilterButton()

        srl_refresh.setColorScheme(R.color.colorPrimaryDark, R.color.colorAccent, R.color.colorPrimaryBlue)
        srl_refresh.setOnRefreshListener {
            heroListFinished = true
            setRoleButtonBackground(4)
            setGameButtonBackground(5)
            disableFilterButtons()
            showHeroList.clear()
            doAsync {
                heroList = NetworkModel().getHeroList()
                heroList.forEach {
                    showHeroList.add(it)
                }
                uiThread {
                    srl_refresh.isRefreshing = false
                    hero_list_view.adapter.notifyDataSetChanged()
                    enableFilterButtons()
                }
            }
        }
    }

    private fun setHeroListView() {
        heroList = (activity as MainActivity).getHeroListJson()
        heroList.forEach {
            showHeroList.add(it)
        }
        heroListFinished = true
        hero_list_view.layoutManager = GridLayoutManager(activity, 4)
        hero_list_view.adapter = HeroListAdapter(showHeroList, object : HeroListAdapter.OnItemClickListener {
            override fun invoke(simpleHeroItem: SimpleHeroItem) {
                val intent = Intent(activity, HeroDisplayActivity::class.java)
                val bundle = Bundle()
                bundle.putString("heroId", simpleHeroItem.id)
                bundle.putString("heroName", simpleHeroItem.name)
                intent.putExtras(bundle)
                startActivity(intent)
            }
        }, activity)
    }

    private fun setFilterButton() {
        setRoleFilter()
        setGameFilter()
    }

    private fun setRoleFilter() {
        val roleButtons = arrayOf(ib_warrior, ib_assassin, ib_specialist, ib_support)
        roleButtons.forEachIndexed{
            index, button -> run{
            button.setOnClickListener { changeHeroList(0, rolePosition = index) }
        }
        }
    }

    private fun setGameFilter() {
        val gameButtons = arrayOf(ib_warcraft, ib_diablo, ib_overwatch, ib_starcraft, ib_retro)
        gameButtons.forEachIndexed{
            index, button -> run{
            button.setOnClickListener {
                changeHeroList(1, gamePosition = index) }
        }
        }
    }

    private fun changeHeroList(click: Int, rolePosition: Int = selectRolePosition, gamePosition: Int = selectGamePosition) {
        val checkRoleFlags = arrayOf("wa", "as", "sp", "su", "all")
        val checkGameFlags = arrayOf("wo", "d3", "ow", "sc", "ot")
        if (!heroListFinished) {
            activity.toast("英雄还在路上")
            return
        }
        if (click == 0) {
            selectRolePosition = if (selectRolePosition === rolePosition) 4 else rolePosition
            setRoleButtonBackground(selectRolePosition)
        } else {
            selectGamePosition = if (selectGamePosition === gamePosition) 5 else gamePosition
            setGameButtonBackground(selectGamePosition)
        }


        showHeroList.clear()
        heroList.forEach {
            val checkRoleFlag = it.role[0].toString() + it.role[1]
            var checkGameFlag = it.game[0].toString() + it.game[1]
            if (selectRolePosition == 4 || checkRoleFlag == checkRoleFlags[rolePosition]) {
                if (selectGamePosition == 5 || checkGameFlag == checkGameFlags[gamePosition]) {
                    showHeroList.add(it)
                }
            }
        }
        hero_list_view.adapter.notifyDataSetChanged()
    }

    private fun enableFilterButtons(){
        val gameButtons = arrayOf(ib_warcraft, ib_diablo, ib_overwatch, ib_starcraft, ib_retro)
        val roleButtons = arrayOf(ib_warrior, ib_assassin, ib_specialist, ib_support)
        gameButtons.forEach{
            it.isClickable = true
        }
        roleButtons.forEach{
            it.isClickable = true
        }
    }

    private fun disableFilterButtons(){
        val gameButtons = arrayOf(ib_warcraft, ib_diablo, ib_overwatch, ib_starcraft, ib_retro)
        val roleButtons = arrayOf(ib_warrior, ib_assassin, ib_specialist, ib_support)
        gameButtons.forEach{
            it.isClickable = false
        }
        roleButtons.forEach{
            it.isClickable = false
        }
    }

    private fun setGameButtonBackground(position: Int) {
        val gameButtons = arrayOf(ib_warcraft, ib_diablo, ib_overwatch, ib_starcraft, ib_retro)
        val onDrawables = arrayOf(R.drawable.main_view_btn_warcraft_on,
                R.drawable.main_view_btn_diablo_on,
                R.drawable.main_view_btn_overwatch_on,
                R.drawable.main_view_btn_starcraft_on,
                R.drawable.main_view_btn_retro_on)
        val offDrawables = arrayOf(R.drawable.main_view_btn_warcraft_off,
                R.drawable.main_view_btn_diablo_off,
                R.drawable.main_view_btn_overwatch_off,
                R.drawable.main_view_btn_starcraft_off,
                R.drawable.main_view_btn_retro_off)
        gameButtons.forEachIndexed {
            index, value ->
            run {
                if (index == position) value.setBackgroundResource(onDrawables[index])
                else value.setBackgroundResource(offDrawables[index])
            }
        }
    }

    private fun setRoleButtonBackground(position: Int) {
        val roleButtons = arrayOf(ib_warrior, ib_assassin, ib_specialist, ib_support)
        val onDrawables = arrayOf(R.drawable.main_view_btn_warrior_on,
                R.drawable.main_view_btn_assassin_on,
                R.drawable.main_view_btn_specialist_on,
                R.drawable.main_view_btn_support_on)
        val offDrawables = arrayOf(R.drawable.main_view_btn_warrior_off,
                R.drawable.main_view_btn_assassin_off,
                R.drawable.main_view_btn_specialist_off,
                R.drawable.main_view_btn_support_off)
        roleButtons.forEachIndexed {
            index, value ->
            run {
                if (index == position) value.setBackgroundResource(onDrawables[index])
                else value.setBackgroundResource(offDrawables[index])
            }
        }
    }


}
