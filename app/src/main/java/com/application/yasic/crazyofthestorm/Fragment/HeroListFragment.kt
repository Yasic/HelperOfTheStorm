package com.application.yasic.crazyofthestorm.Fragment


import android.content.Intent
import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import com.application.yasic.crazyofthestorm.Activity.HeroDisplayActivity
import com.application.yasic.crazyofthestorm.Activity.MainActivity
import com.application.yasic.crazyofthestorm.Model.NetworkModel
import com.application.yasic.crazyofthestorm.Util.HeroListAdapter
import com.application.yasic.crazyofthestorm.Object.SimpleHeroItem
import com.application.yasic.crazyofthestorm.R
import kotlinx.android.synthetic.main.fragment_hero_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class HeroListFragment : Fragment() {
    private var heroListLoadFinished = false
    private var roleFilterType = ALLROLE
    private var gameFilterType = ALLGAME
    private var showHeroList: MutableList<SimpleHeroItem> = mutableListOf()
    private var heroList: MutableList<SimpleHeroItem> = mutableListOf()

    var gameButtonMap = mapOf<String, ImageButton>()

    var roleButtonMap = mapOf<String, ImageButton>()

    companion object{
        private val ROLEBUTTON = 0
        private val GAMEBUTTON = 1

        private val WARCRAFT = "wo"
        private val DIABLO = "d3"
        private val OVERWATCH = "ow"
        private val STARCRAFT = "sc"
        private val OTHER = "ot"
        private val ALLGAME = "al"

        private val WARRIOR = "wa"
        private val ASSASSIN = "as"
        private val SUPPORT = "su"
        private val SPECIALIST = "sp"
        private val ALLROLE = "al"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_hero_list, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gameButtonMap = mapOf<String, ImageButton>(Pair(WARCRAFT, ib_warcraft),
                Pair(OVERWATCH, ib_overwatch),
                Pair(STARCRAFT, ib_starcraft),
                Pair(DIABLO, ib_diablo),
                Pair(OTHER, ib_retro))
        roleButtonMap = mapOf<String, ImageButton>(Pair(WARRIOR, ib_warrior),
                Pair(ASSASSIN, ib_assassin),
                Pair(SPECIALIST, ib_specialist),
                Pair(SUPPORT, ib_support))

        heroList = (activity as MainActivity).getHeroListJson()
        initHeroListView(heroList)
        setFilterButtonClickEvent()

        srl_refresh.setColorScheme(R.color.colorPrimary, R.color.colorAccent, R.color.colorGreen)
        srl_refresh.setOnRefreshListener {
            heroListLoadFinished = true
            clearRoleButtonBackground()
            clearGameButtonBackground()
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

    private fun initHeroListView(heroList: MutableList<SimpleHeroItem>) {
        heroList.forEach {
            showHeroList.add(it)
        }
        heroListLoadFinished = true
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

    private fun setFilterButtonClickEvent() {
        setRoleFilter()
        setGameFilter()
    }

    private fun setRoleFilter() {
        roleButtonMap.forEach{
            val tag = it.key
            it.value.setOnClickListener { filterHeroList(ROLEBUTTON, tag) }
        }
    }

    private fun setGameFilter() {
        gameButtonMap.forEach{
            val tag = it.key
            it.value.setOnClickListener { filterHeroList(GAMEBUTTON, tag) }
        }
    }

    private fun filterHeroList(clickType: Int, clickItemType: String){
        if (!heroListLoadFinished) {
            activity.toast("英雄还在路上")
            return
        }
        if (clickType == ROLEBUTTON){
            roleFilterType = if (roleFilterType == clickItemType) ALLROLE else clickItemType
            clearRoleButtonBackground()
            if (roleFilterType != ALLROLE){
                setRoleButtonActiveBackground(roleFilterType)
            }
        } else{
            gameFilterType = if (gameFilterType == clickItemType) ALLGAME else clickItemType
            clearGameButtonBackground()
            if (gameFilterType != ALLGAME){
                setGameButtonActiveBackground(gameFilterType)
            }
        }

        showHeroList.clear()
        heroList.forEach {
            val checkRoleFlag = it.role[0].toString() + it.role[1]
            val checkGameFlag = it.game[0].toString() + it.game[1]

            if (roleFilterType == ALLROLE || checkRoleFlag == roleFilterType){
                if (gameFilterType == ALLGAME || checkGameFlag == gameFilterType){
                    showHeroList.add(it)
                }
            }
        }
        hero_list_view.adapter.notifyDataSetChanged()
    }

    private fun clearRoleButtonBackground(){
        val roleDrawableOffMap = mapOf(Pair(WARRIOR, R.drawable.main_view_btn_warrior_off),
                Pair(ASSASSIN, R.drawable.main_view_btn_assassin_off),
                Pair(SPECIALIST, R.drawable.main_view_btn_specialist_off),
                Pair(SUPPORT, R.drawable.main_view_btn_support_off))
        roleButtonMap.forEach{
            it.value.setBackgroundResource(roleDrawableOffMap[it.key]!!)
        }
    }

    private fun setRoleButtonActiveBackground(roleFilterType: String) {
        val roleDrawableOnMap = mapOf(Pair(WARRIOR, R.drawable.main_view_btn_warrior_on),
                Pair(ASSASSIN, R.drawable.main_view_btn_assassin_on),
                Pair(SPECIALIST, R.drawable.main_view_btn_specialist_on),
                Pair(SUPPORT, R.drawable.main_view_btn_support_on))
        roleButtonMap[roleFilterType]!!.setBackgroundResource(roleDrawableOnMap[roleFilterType]!!)
    }

    private fun clearGameButtonBackground(){
        val gameDrawableOffMap = mapOf(Pair(WARCRAFT, R.drawable.main_view_btn_warcraft_off),
                Pair(OVERWATCH, R.drawable.main_view_btn_overwatch_off),
                Pair(STARCRAFT, R.drawable.main_view_btn_starcraft_off),
                Pair(DIABLO, R.drawable.main_view_btn_diablo_off),
                Pair(OTHER, R.drawable.main_view_btn_retro_off))
        gameButtonMap.forEach{
            it.value.setBackgroundResource(gameDrawableOffMap[it.key]!!)
        }
    }

    private fun setGameButtonActiveBackground(roleFilterType: String) {
        val gameDrawableOnMap = mapOf(Pair(WARCRAFT, R.drawable.main_view_btn_warcraft_on),
                Pair(OVERWATCH, R.drawable.main_view_btn_overwatch_on),
                Pair(STARCRAFT, R.drawable.main_view_btn_starcraft_on),
                Pair(DIABLO, R.drawable.main_view_btn_diablo_on),
                Pair(OTHER, R.drawable.main_view_btn_retro_on))
        gameButtonMap[roleFilterType]!!.setBackgroundResource(gameDrawableOnMap[roleFilterType]!!)
    }

    private fun enableFilterButtons(){
        gameButtonMap.forEach{
            it.value.isClickable = true
        }
        roleButtonMap.forEach{
            it.value.isClickable = true
        }
    }

    private fun disableFilterButtons(){
        gameButtonMap.forEach{
            it.value.isClickable = false
        }
        roleButtonMap.forEach{
            it.value.isClickable = false
        }
    }


}
