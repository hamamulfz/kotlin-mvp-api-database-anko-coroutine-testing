package com.example.fauzi.selectedmatchschedule.tablayout

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MyPagerAdapter(fragmentManager: FragmentManager?) : FragmentPagerAdapter(fragmentManager){

    var fragmentList = arrayListOf<Fragment>()
    var titleList = arrayListOf<String>()

    fun populateFragment(fragment: Fragment,title: String){
        fragmentList.add(fragment)
        titleList.add(title)
    }
    override fun getItem(position: Int) = fragmentList[position]

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int) = titleList[position]
}