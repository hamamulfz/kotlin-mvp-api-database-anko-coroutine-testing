package com.example.fauzi.selectedmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fauzi.footbalclub.FavoriteTeamsFragment
import com.example.fauzi.selectedmatchschedule.R.id.*
import com.example.fauzi.selectedmatchschedule.favorite.match.FavoriteMatchFragment
import com.example.fauzi.selectedmatchschedule.list.main.MainFragment
import com.example.fauzi.selectedmatchschedule.R.layout.activity_home
import com.example.fauzi.selectedmatchschedule.list.team.TeamFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                teams -> {
                    loadMatchListFragment(savedInstanceState)
                }
                favorite_match -> {
                    loadFavoritesMatchFragment(savedInstanceState)
                }
                club -> {
                    loadTeamFragment(savedInstanceState)
                }
                favorite_team -> {
                    loadFavoritesTeamFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = teams
    }

    private fun loadMatchListFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MainFragment(), MainFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadFavoritesMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteMatchFragment(), FavoriteMatchFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadFavoritesTeamFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteTeamsFragment(), FavoriteTeamsFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadTeamFragment(savedInstanceState: Bundle?){
        if(savedInstanceState==null){
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamFragment(), TeamFragment::class.java.simpleName)
                    .commit()

        }
    }
}
