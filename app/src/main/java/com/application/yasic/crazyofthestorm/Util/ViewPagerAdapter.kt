package com.application.yasic.crazyofthestorm.Util


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup


class ViewPagerAdapter(val fragmentManager: FragmentManager, val tabTitleList: MutableList<String>, val fragmentList: MutableList<Fragment>): FragmentPagerAdapter(fragmentManager) {


    override fun getItem(position: Int): Fragment? {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        if (tabTitleList == null){
            return 0
        }
        return tabTitleList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabTitleList[position]
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any?) {
        super.destroyItem(container, position, `object`)
    }

}


