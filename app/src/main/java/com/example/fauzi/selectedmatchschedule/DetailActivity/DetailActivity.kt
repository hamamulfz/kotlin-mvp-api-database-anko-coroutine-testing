package com.example.fauzi.selectedmatchschedule.DetailActivity

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.fauzi.selectedmatchschedule.Api.ApiRepository
import com.example.fauzi.selectedmatchschedule.Favorite.Favorite
import com.example.fauzi.selectedmatchschedule.Favorite.database
import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.R.color.colorAccent
import com.example.fauzi.selectedmatchschedule.R.drawable.*
import com.example.fauzi.selectedmatchschedule.R.id.*
import com.example.fauzi.selectedmatchschedule.R.menu.detail_menu
import com.example.fauzi.selectedmatchschedule.Response.LeagueBadge
import com.example.fauzi.selectedmatchschedule.Response.MatchList
import com.example.fauzi.selectedmatchschedule.Response.TeamBadge
import com.example.fauzi.selectedmatchschedule.Utils.visible
import com.example.fauzi.selectedmatchschedule.Utils.invisible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.swipeRefreshLayout


class DetailActivity : AppCompatActivity(), DetailView {

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var selectedMatch: String
    private lateinit var presenter: DetailPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var eventsData : MatchList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        selectedMatch = intent.getStringExtra("match")

        linearLayout {
            lparams(width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                        android.R.color.holo_green_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_red_light)

                relativeLayout {
                    lparams(width = matchParent, height = wrapContent) {
                        padding = dip(5)
                    }


                    linearLayout {
                        lparams(width = matchParent, height = wrapContent)
                        orientation = LinearLayout.VERTICAL

                        //League Name and badge
                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER

                            imageView {
                                id = R.id.d_leagueBadge
                            }.lparams(100, 100) {}

                            textView {
                                textSize = 20f
                                id = R.id.d_leagueName
                                textColor = Color.BLUE
                            }
                        }

                        //line
                        linearLayout {
                            view {
                                backgroundColor = Color.BLUE
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                        }

                        //Match Date
                        textView {
                            gravity = Gravity.CENTER
                            textSize = 18f

                            id = R.id.d_dateOfMatch
                            textColor = Color.BLUE
                        }

                        linearLayout {
                            orientation = LinearLayout.HORIZONTAL
                            gravity = Gravity.CENTER

                            //Home Badge dan Team Name
                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                gravity = Gravity.CENTER

                                imageView {
                                    id = R.id.d_homeTeamBadge
                                }.lparams(100, 100) {}

                                textView {
                                    gravity = Gravity.CENTER
                                    textSize = 18f
                                    id = R.id.d_homeTeamName
                                    textColor = Color.BLUE
                                }
                            }.lparams(width = 0, height = wrapContent) {
                                weight = 0.4f
                            }

                            //Match Score
                            linearLayout {
                                gravity = Gravity.CENTER

                                textView {
                                    id = R.id.d_homeScore
                                    padding = dip(4)
                                    textSize = 20f
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    text = "0"
                                    //text=selectedMatch.homeScore
                                }.lparams(width = 0, height = wrapContent) {
                                    weight = 4.5f
                                }

                                textView {
                                    text = "vs"
                                }.lparams(width = 0, height = wrapContent) {
                                    weight = 1f
                                }

                                textView {
                                    id = R.id.d_awayScore
                                    padding = dip(4)
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textSize = 20f
                                    text = "0"
                                }.lparams(width = 0, height = wrapContent) {
                                    weight = 4.5f
                                }
                            }.lparams(0, wrapContent) {
                                weight = 0.2f
                            }

                            //Away Badge dan Team Name
                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                gravity = Gravity.CENTER

                                imageView {
                                    id = R.id.d_awayTeamBadge
                                }.lparams(100, 100) {}

                                textView {
                                    gravity = Gravity.CENTER
                                    textSize = 18f
                                    id = R.id.d_awayTeamName
                                    textColor = Color.BLUE
                                }
                            }.lparams(0, wrapContent) {
                                weight = 0.4f
                            }
                        }

                        //line
                        linearLayout {
                            view {
                                backgroundColor = Color.BLUE
                            }.lparams(matchParent, dip(1)) {
                                topMargin = dip(8)
                            }
                        }

                        scrollView {
                            linearLayout {
                                orientation = LinearLayout.VERTICAL
                                topPadding = dip(10)
                                bottomPadding = dip(20)

                                //Goals Detail
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamGoalDetail
                                        gravity = Gravity.RIGHT
                                        if (text == "") {
                                            text = "-"
                                        }
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = "Goals"
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.LEFT
                                        leftPadding = dip(4)
                                        id = R.id.d_awayTeamGoalDetail
                                        if (text == "") {
                                            text = "-"
                                        }
                                    }.lparams(0, wrapContent, 4f)
                                }

                                //Formation
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamFormation
                                        gravity = Gravity.RIGHT
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = "Formation"
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.LEFT
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamFormation
                                    }.lparams(0, wrapContent, 4f)
                                }

                                //line
                                linearLayout {
                                    view {
                                        backgroundColor = Color.LTGRAY
                                    }.lparams(matchParent, dip(1)) {
                                        topMargin = dip(8)
                                    }
                                }


                                //LineUp
                                textView {
                                    gravity = Gravity.CENTER
                                    textSize = 15f
                                    text = "Line Up"
                                    padding = dip(5)
                                    textColor = Color.WHITE
                                    backgroundColor = Color.BLUE
                                }

                                //Forward
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamLineupForward
                                        gravity = Gravity.RIGHT
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = "Forward"
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.LEFT
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamLineupForward

                                    }.lparams(0, wrapContent, 4f)
                                }

                                //Mid Field
                                linearLayout {
                                    topPadding = dip(8)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamLineupMidfield
                                        gravity = Gravity.RIGHT
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = "Mid Field"
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.LEFT
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamLineupMidfield
                                    }.lparams(0, wrapContent, 4f)
                                }

                                //Defense
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamLineupDefense
                                        gravity = Gravity.RIGHT
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = "Defense"
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.LEFT
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamLineupDefense
                                    }.lparams(0, wrapContent, 4f)
                                }

                                //Goal Keeper
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamLineupGoalkeeper
                                        gravity = Gravity.RIGHT
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = "Goal Keeper"
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.LEFT
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamLineupGoalkeeper
                                    }.lparams(0, wrapContent, 4f)
                                }

                                //substitutes
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamLineupSubstitutes
                                        gravity = Gravity.RIGHT
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = "Substitutes"
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.LEFT
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamLineupSubstitutes
                                    }.lparams(0, wrapContent, 4f)
                                }

                                //line
                                linearLayout {
                                    view {
                                        backgroundColor = Color.LTGRAY
                                    }.lparams(matchParent, dip(1)) {
                                        topMargin = dip(8)
                                    }
                                }

                                //Match Card
                                textView {
                                    gravity = Gravity.CENTER
                                    textSize = 15f
                                    text = "Match Card"
                                    padding = dip(5)
                                    textColor = Color.WHITE
                                    backgroundColor = Color.BLUE
                                }

                                //Yellow Card
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamYellowCards
                                        gravity = Gravity.RIGHT
                                        if (text == "") {
                                            text = "-"
                                        }
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = "Yellow Card"
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.LEFT
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamYellowCards
                                        if (text == "") {
                                            text = "-"
                                        }
                                    }.lparams(0, wrapContent, 4f)
                                }

                                //red Card
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamRedCards
                                        gravity = Gravity.RIGHT
                                        if (text == "") {
                                            text = "-"
                                        }
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = "Red Card"
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.LEFT
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamRedCards
                                        if (text == "") {
                                            text = "-"
                                        }
                                    }.lparams(0, wrapContent, 4f)
                                }
                            }
                        }

                    }

                    progressBar = progressBar {
                        id = R.id.progresBar
                    }.lparams {
                        centerInParent()
                    }
                }
            }
        }

        favoriteState()
        progressBar = find(R.id.progresBar)
        presenter = DetailPresenter(this, ApiRepository(), Gson())
        presenter.getEventDetail(selectedMatch)
        supportActionBar?.title = getString(R.string.detail_name)
    }

    override fun showHomeTeamBadge(dataHomeTeam: List<TeamBadge>) {
         val imgHomeTeamBadge = find<ImageView>(R.id.d_homeTeamBadge)
        Picasso.get()
                .load(dataHomeTeam[0].matchTeamBadge)
                .into(imgHomeTeamBadge)
    }

    override fun showAwayTeamBadge(dataAwayTeam: List<TeamBadge>) {
        val imgAwayTeamBadge = find<ImageView>(R.id.d_awayTeamBadge)
        Picasso.get()
                .load(dataAwayTeam[0].matchTeamBadge)
                .into(imgAwayTeamBadge)
    }

    override fun showLeagueBadge(dataLeague: List<LeagueBadge>) {
        val leagueBadgeUrl = find<ImageView>(R.id.d_leagueBadge)
        Picasso.get()
                .load(dataLeague[0].leagueBadgeUrl)
                .into(leagueBadgeUrl)
    }

    override fun showEventDetail(dataEvent: List<MatchList>) {

        val s_matchDate = find<TextView>(R.id.d_dateOfMatch)
        val s_leagueName = find<TextView>(R.id.d_leagueName)
        val s_homeTeamName = find<TextView>(R.id.d_homeTeamName)
        val s_awayTeamName = find<TextView>(R.id.d_awayTeamName)
        val s_homeTeamScore = find<TextView>(R.id.d_homeScore)
        val s_awayTeamScore = find<TextView>(R.id.d_awayScore)
        val s_homeTeamGoalDetail= find<TextView>(R.id.d_homeTeamGoalDetail)
        val s_awayTeamGoalDetail = find<TextView>(R.id.d_awayTeamGoalDetail)
        val s_homeTeamFormation = find<TextView>(R.id.d_homeTeamFormation)
        val s_awayTeamFormation = find<TextView>(d_awayTeamFormation)
        val s_homeTeamLineupForward = find<TextView>(d_homeTeamLineupForward)
        val s_awayTeamLineupForward = find<TextView>(d_awayTeamLineupForward)
        val s_homeTeamLineupMidfield = find<TextView>(d_homeTeamLineupMidfield)
        val s_awayTeamLineupMidfield =find<TextView>(d_awayTeamLineupMidfield)
        val s_homeTeamLineupDefense = find<TextView>(d_homeTeamLineupDefense)
        val s_awayTeamLineupDefense = find<TextView>(d_awayTeamLineupDefense)
        val s_homeTeamLineupGoalkeeper = find<TextView>(d_homeTeamLineupGoalkeeper)
        val s_awayTeamLineupGoalkeeper = find<TextView>(d_awayTeamLineupGoalkeeper)
        val s_homeTeamLineupSubstitutes = find<TextView>(d_homeTeamLineupSubstitutes)
        val s_homeTeamYellowCards = find<TextView>(d_homeTeamYellowCards)
        val s_awayTeamYellowCards = find<TextView>(d_awayTeamYellowCards)
        val s_homeTeamRedCards = find<TextView>(d_homeTeamRedCards)
        val s_awayTeamRedCards = find<TextView>(d_awayTeamRedCards)
        val s_awayTeamLineupSubstitutes = find<TextView>(d_awayTeamLineupSubstitutes)

        eventsData = MatchList(dataEvent[0].eventId,
                dataEvent[0].dateOfMatch,
                dataEvent[0].awayTeamId,
                dataEvent[0].homeTeamId,
                dataEvent[0].homeScore,
                dataEvent[0].awayScore,
                dataEvent[0].awayTeamVsHomeTeam,
                dataEvent[0].leagueId,
                dataEvent[0].leagueName,
                dataEvent[0].awayTeamFormation,
                dataEvent[0].awayTeamName,
                dataEvent[0].awayTeamGoalDetails,
                dataEvent[0].awayTeamLineupDefense,
                dataEvent[0].awayTeamLineupForward,
                dataEvent[0].awayTeamLineupGoalkeeper,
                dataEvent[0].awayTeamLineupMidfield,
                dataEvent[0].awayTeamLineupSubstitutes,
                dataEvent[0].awayTeamYellowCards,
                dataEvent[0].awayTeamRedCards,
                dataEvent[0].homeTeamName,
                dataEvent[0].homeTeamGoalDetails,
                dataEvent[0].homeTeamFormation,
                dataEvent[0].homeTeamLineupDefense,
                dataEvent[0].homeTeamLineupForward,
                dataEvent[0].homeTeamLineupGoalkeeper,
                dataEvent[0].homeTeamLineupMidfield,
                dataEvent[0].homeTeamLineupSubstitutes,
                dataEvent[0].homeTeamRedCards,
                dataEvent[0].homeTeamYellowCards
        )

        s_matchDate.text = dataEvent[0].dateOfMatch
        s_leagueName.text = dataEvent[0].leagueName
        s_homeTeamName.text = dataEvent[0].homeTeamName
        s_awayTeamName.text = dataEvent[0].awayTeamName
        s_homeTeamScore.text = dataEvent[0].homeScore
        s_awayTeamScore.text = dataEvent[0].awayScore
        s_homeTeamGoalDetail.text = dataEvent[0].homeTeamGoalDetails
        s_awayTeamGoalDetail.text = dataEvent[0].awayTeamGoalDetails
        s_homeTeamFormation.text = dataEvent[0].homeTeamFormation
        s_awayTeamFormation.text = dataEvent[0].awayTeamFormation
        s_homeTeamLineupForward.text = dataEvent[0].homeTeamLineupForward
        s_awayTeamLineupForward.text = dataEvent[0].awayTeamLineupForward
        s_homeTeamLineupMidfield.text = dataEvent[0].homeTeamLineupMidfield
        s_awayTeamLineupMidfield.text = dataEvent[0].awayTeamLineupMidfield
        s_homeTeamLineupDefense.text = dataEvent[0].homeTeamLineupDefense
        s_awayTeamLineupDefense.text = dataEvent[0].awayTeamLineupDefense
        s_homeTeamLineupGoalkeeper.text = dataEvent[0].homeTeamLineupGoalkeeper
        s_awayTeamLineupGoalkeeper.text = dataEvent[0].awayTeamLineupGoalkeeper
        s_homeTeamLineupSubstitutes.text = dataEvent[0].homeTeamLineupSubstitutes
        s_awayTeamLineupSubstitutes.text = dataEvent[0].awayTeamLineupSubstitutes
        s_homeTeamYellowCards.text = dataEvent[0].awayTeamYellowCards
        s_awayTeamYellowCards.text = dataEvent[0].homeTeamYellowCards
        s_homeTeamRedCards.text = dataEvent[0].awayTeamYellowCards
        s_awayTeamRedCards.text = dataEvent[0].homeTeamRedCards

        presenter.getBadgeHomeTeam(eventsData.homeTeamId)
        presenter.getBadgeAwayTeam(eventsData.awayTeamId)
        presenter.getBadgeLeague(eventsData.leagueId)
    }

    override fun startLoading() {
        progressBar.visible()    }

    override fun endLoading() {
        progressBar.invisible()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE)
                    .whereArgs("(ID_EVENT = {id})",
                            "id" to selectedMatch)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()) isFavorite = true
        }
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
                insert(Favorite.TABLE_FAVORITE,
                        Favorite.ID_EVENT to eventsData.eventId,
                        Favorite.LEAGUE_ID to eventsData.leagueId,
                        Favorite.AWAY_ID to eventsData.homeTeamId,
                        Favorite.HOME_ID to eventsData.awayTeamId,
                        Favorite.DATE to eventsData.dateOfMatch,
                        Favorite.HOME_SCORE to eventsData.homeScore,
                        Favorite.AWAY_SCORE to eventsData.awayScore,
                        Favorite.HOME_VS_AWAY to eventsData.awayTeamVsHomeTeam
                        )
            }
            snackbar(swipeRefresh, "Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE, "(ID_EVENT = {id})",
                        "id" to selectedMatch)
            }
            snackbar(swipeRefresh, "Removed from favorite").show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_favorite)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_favorite)
    }
}
