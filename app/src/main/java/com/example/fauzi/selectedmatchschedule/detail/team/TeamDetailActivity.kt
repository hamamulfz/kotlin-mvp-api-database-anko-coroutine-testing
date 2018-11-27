package com.example.fauzi.selectedmatchschedule.detail.team

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import com.example.fauzi.footbalclub.Favorite
import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.R.color.colorAccent
import com.example.fauzi.selectedmatchschedule.R.color.colorPrimaryDark
import com.example.fauzi.selectedmatchschedule.R.drawable.ic_add_favorite
import com.example.fauzi.selectedmatchschedule.R.drawable.ic_add_favorite_cancel
import com.example.fauzi.selectedmatchschedule.R.id.add_to_favorite
import com.example.fauzi.selectedmatchschedule.R.menu.detail_menu
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.favorite.database
import com.example.fauzi.selectedmatchschedule.detail.player.PlayerDetailActivity
import com.example.fauzi.selectedmatchschedule.response.PlayerDetail
import com.example.fauzi.selectedmatchschedule.response.TeamBadge
import com.example.fauzi.selectedmatchschedule.utils.invisible
import com.example.fauzi.selectedmatchschedule.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout



class TeamDetailActivity : AppCompatActivity(), TeamDetailView {


    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: TeamBadge
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter: PlayerAdapter
    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView
    private lateinit var scrollView: ScrollView
    private var player : MutableList<PlayerDetail> = mutableListOf()
    private lateinit var headView: LinearLayout
    private lateinit var buttonView: LinearLayout
    private lateinit var overviewButton: Button
    private lateinit var playerListButton: Button


    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var idTeam: String
    private  var status: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        idTeam = intent.getStringExtra("id")
        supportActionBar?.title = resources.getString(R.string.team_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout {
            lparams( matchParent,
                    wrapContent)
            orientation = LinearLayout.VERTICAL
            backgroundColor = Color.WHITE

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams (width = matchParent, height = matchParent)

                    headView = linearLayout {
                        lparams(matchParent,
                                wrapContent)
                        orientation = LinearLayout.VERTICAL
                        gravity = Gravity.CENTER
                        id = R.id.headView


                        linearLayout {
                            lparams(matchParent,
                                    wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER

                            teamBadge = imageView {
                            }.lparams(100,
                                    100) {}

                            teamName = textView {
                                textSize = 40f
                                textColor = Color.BLUE
                            }.lparams {
                                leftMargin = dip(20)
                            }
                        }.lparams{
                            topMargin = dip(10)
                        }

                        teamFormedYear = textView {
                            this.gravity = Gravity.CENTER
                        }

                        teamStadium = textView {
                            this.gravity = Gravity.CENTER
                            textColor = ContextCompat.getColor(context, colorPrimaryDark)
                        }
                    }

                    buttonView = linearLayout {
                        lparams(matchParent,
                                wrapContent)
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.HORIZONTAL
                        id = R.id.buttonView

                        overviewButton = button {
                            id = R.id.overview
                            text = resources.getString(R.string.overview)
                            onClick {
                                presenter.getTeamDetail(idTeam)
                                status = 1
                            }
                            backgroundColor = colorPrimaryDark
                            textColor = Color.WHITE
                        }.lparams(width = 0, height = wrapContent){
                            weight = 1f
                        }

                        playerListButton = button {
                            id = R.id.showPlayerList
                            text = resources.getString(R.string.playerList)
                            onClick {
                                presenter.getPlayerList(idTeam)
                                status = 2
                            }
                            backgroundColor = colorPrimaryDark
                            textColor = Color.WHITE

                        }.lparams(width = 0, height = wrapContent){
                            weight = 1f
                        }
                    }.lparams(matchParent, wrapContent){
                        bottomOf(R.id.headView)
                        topMargin = dip(10)
                    }

                    recyclerView = recyclerView {
                        id = R.id.recycler_view
                        layoutManager = LinearLayoutManager(ctx)
                    }.lparams(matchParent, matchParent) {
                        bottomOf(buttonView)
                    }

                    scrollView = scrollView {
                        isVerticalScrollBarEnabled = false
                        relativeLayout {
                            lparams(matchParent,
                                    wrapContent)

                            linearLayout {
                                lparams(matchParent,
                                        wrapContent)
                                orientation = LinearLayout.VERTICAL
                                gravity = Gravity.CENTER_HORIZONTAL
                                padding = dip (10)

                                teamDescription = textView().lparams {
                                    topMargin = dip(5)

                                }
                            }
                            progressBar = progressBar {
                            }.lparams {
                                centerHorizontally()
                            }
                        }
                    }.lparams {  bottomOf(buttonView) }
                }
            }
        }

        favoriteState()
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(idTeam)

        swipeRefresh.onRefresh {
            presenter.getTeamDetail(idTeam)
        }

        adapter = PlayerAdapter(player) {
            this.startActivity<PlayerDetailActivity>("id" to "${it.playerId}")
            Log.d("tag", it.playerId )
        }
        recyclerView.adapter = adapter
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE_TEAM)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to idTeam)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun showPlayerList(data: List<PlayerDetail>) {
        swipeRefresh.isRefreshing = false
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()

    }

    override fun showRV() {
        recyclerView.visible()
        scrollView.invisible()
    }

    override fun hideRV() {
        recyclerView.invisible()
        scrollView.visible()
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamDetail(data: List<TeamBadge>) {
        teams = TeamBadge(data[0].matchTeamBadge,
                data[0].teamId,
                data[0].teamName,
                data[0].teamFormedYear,
                data[0].teamStadium,
                data[0].teamDescription)
        swipeRefresh.isRefreshing = false
        Picasso.get().load(data[0].matchTeamBadge).into(teamBadge)
        teamName.text = data[0].teamName
        teamDescription.text = data[0].teamDescription
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE_TEAM,
                        Favorite.TEAM_ID to teams.teamId,
                        Favorite.TEAM_NAME to teams.teamName,
                        Favorite.TEAM_BADGE to teams.matchTeamBadge)
            }
            swipeRefresh.snackbar( "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar( e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                        "id" to idTeam)
            }
            swipeRefresh.snackbar( "Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar( e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_favorite_cancel)
    }
}
