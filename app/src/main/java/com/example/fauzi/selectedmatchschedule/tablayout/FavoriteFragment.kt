package com.example.fauzi.selectedmatchschedule.tablayout


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.favorite.match.FavoriteMatchFragment
import com.example.fauzi.selectedmatchschedule.favorite.team.FavoriteTeamsFragment
import kotlinx.android.synthetic.main.fragment_favorite.*


class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
         //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vPager = view.findViewById<ViewPager>(R.id.viewpager_main)
        val tabs = view.findViewById<TabLayout>(R.id.tabs_main)
        val adapter = MyPagerAdapter(childFragmentManager)
        setHasOptionsMenu(true)
        adapter.populateFragment(FavoriteMatchFragment(), "Matchs")
        adapter.populateFragment(FavoriteTeamsFragment(), "Teams")
        vPager.adapter = adapter
        tabs.setupWithViewPager(vPager)
    }

}
