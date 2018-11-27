package com.example.fauzi.selectedmatchschedule

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.fauzi.selectedmatchschedule.Favorite.FavoriteTeamsFragment
import com.example.fauzi.selectedmatchschedule.MainActivity.MainFragment
import com.example.fauzi.selectedmatchschedule.R.id.favorites
import com.example.fauzi.selectedmatchschedule.R.id.teams
import com.example.fauzi.selectedmatchschedule.R.layout.activity_home
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_home)

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                teams -> {
                    loadTeamsFragment(savedInstanceState)
                }
                favorites -> {
                    loadFavoritesFragment(savedInstanceState)
                }
            }
            true
        }
        bottom_navigation.selectedItemId = teams
    }

    private fun loadTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MainFragment(), MainFragment::class.java.simpleName)
                    .commit()
        }
    }

    private fun loadFavoritesFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, FavoriteTeamsFragment(), FavoriteTeamsFragment::class.java.simpleName)
                    .commit()
        }
    }
}
