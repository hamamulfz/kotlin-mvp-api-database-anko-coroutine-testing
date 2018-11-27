package com.example.fauzi.selectedmatchschedule.detail

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
import com.example.fauzi.selectedmatchschedule.api.ApiRepository
import com.example.fauzi.selectedmatchschedule.favorite.Favorite
import com.example.fauzi.selectedmatchschedule.favorite.database
import com.example.fauzi.selectedmatchschedule.R
import com.example.fauzi.selectedmatchschedule.R.color.colorAccent
import com.example.fauzi.selectedmatchschedule.R.drawable.*
import com.example.fauzi.selectedmatchschedule.R.id.*
import com.example.fauzi.selectedmatchschedule.R.menu.detail_menu
import com.example.fauzi.selectedmatchschedule.response.LeagueBadge
import com.example.fauzi.selectedmatchschedule.response.MatchList
import com.example.fauzi.selectedmatchschedule.response.TeamBadge
import com.example.fauzi.selectedmatchschedule.utils.visible
import com.example.fauzi.selectedmatchschedule.utils.invisible
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
                                    text = ctx.resources.getString(R.string.zero)
                                    //text=selectedMatch.homeScore
                                }.lparams(width = 0, height = wrapContent) {
                                    weight = 4.5f
                                }

                                textView {
                                    text = ctx.resources.getString(R.string.vs)
                                }.lparams(width = 0, height = wrapContent) {
                                    weight = 1f
                                }

                                textView {
                                    id = R.id.d_awayScore
                                    padding = dip(4)
                                    gravity = Gravity.CENTER_HORIZONTAL
                                    textSize = 20f
                                    text = ctx.resources.getString(R.string.zero)
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
                                        gravity = Gravity.END
                                        if (text == "") {
                                            text = ctx.resources.getString(R.string.hypen)
                                        }
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = ctx.resources.getString(R.string.goals)
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.START
                                        leftPadding = dip(4)
                                        id = R.id.d_awayTeamGoalDetail
                                        if (text == "") {
                                            text = ctx.resources.getString(R.string.hypen)
                                        }
                                    }.lparams(0, wrapContent, 4f)
                                }

                                //Formation
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamFormation
                                        gravity = Gravity.END
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = ctx.resources.getString(R.string.formation)
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.START
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
                                    text = ctx.resources.getString(R.string.line_up)
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
                                        gravity = Gravity.END
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = ctx.resources.getString(R.string.forward)
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.START
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
                                        gravity = Gravity.END
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = ctx.resources.getString(R.string.midfield)
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.START
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
                                        gravity = Gravity.END
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = ctx.resources.getString(R.string.defense)
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.START
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
                                        gravity = Gravity.END
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = ctx.resources.getString(R.string.goal_keeper)
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.START
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
                                        gravity = Gravity.END
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = ctx.resources.getString(R.string.substites)
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.START
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
                                    text = ctx.resources.getString(R.string.match_cards)
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
                                        gravity = Gravity.END
                                        if (text == "") {
                                            text = ctx.resources.getString(R.string.hypen)
                                        }
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = ctx.resources.getString(R.string.yellow_card)
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.START
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamYellowCards
                                        if (text == "") {
                                            text = ctx.resources.getString(R.string.hypen)
                                        }
                                    }.lparams(0, wrapContent, 4f)
                                }

                                //red Card
                                linearLayout {
                                    topPadding = dip(4)

                                    textView {
                                        rightPadding = dip(8)
                                        id = R.id.d_homeTeamRedCards
                                        gravity = Gravity.END
                                        if (text == "") {
                                            text = ctx.resources.getString(R.string.hypen)
                                        }
                                    }.lparams(0, wrapContent, 4f)

                                    textView {
                                        gravity = Gravity.CENTER
                                        leftPadding = dip(8)
                                        rightPadding = dip(8)
                                        text = ctx.resources.getString(R.string.red_card)
                                        textColor = Color.BLUE
                                    }.lparams(0, wrapContent, 2f)

                                    textView {
                                        gravity = Gravity.START
                                        leftPadding = dip(8)
                                        id = R.id.d_awayTeamRedCards
                                        if (text == "") {
                                            text = ctx.resources.getString(R.string.hypen)
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

    override fun showEventDetail(dataEvents: List<MatchList>) {

        val sMatchDate = find<TextView>(R.id.d_dateOfMatch)
        val sLeagueName = find<TextView>(R.id.d_leagueName)
        val sHomeTeamName = find<TextView>(R.id.d_homeTeamName)
        val sAwayTeamName = find<TextView>(R.id.d_awayTeamName)
        val sHomeTeamScore = find<TextView>(R.id.d_homeScore)
        val sAwayTeamScore = find<TextView>(R.id.d_awayScore)
        val sHomeTeamGoalDetail= find<TextView>(R.id.d_homeTeamGoalDetail)
        val sAwayTeamGoalDetail = find<TextView>(R.id.d_awayTeamGoalDetail)
        val sHomeTeamFormation = find<TextView>(R.id.d_homeTeamFormation)
        val sAwayTeamFormation = find<TextView>(d_awayTeamFormation)
        val sHomeTeamLineupForward = find<TextView>(d_homeTeamLineupForward)
        val sAwayTeamLineupForward = find<TextView>(d_awayTeamLineupForward)
        val sHomeTeamLineupMidfield = find<TextView>(d_homeTeamLineupMidfield)
        val sAwayTeamLineupMidfield =find<TextView>(d_awayTeamLineupMidfield)
        val sHomeTeamLineupDefense = find<TextView>(d_homeTeamLineupDefense)
        val sAwayTeamLineupDefense = find<TextView>(d_awayTeamLineupDefense)
        val sHomeTeamLineupGoalkeeper = find<TextView>(d_homeTeamLineupGoalkeeper)
        val sAwayTeamLineupGoalkeeper = find<TextView>(d_awayTeamLineupGoalkeeper)
        val sHomeTeamYellowCards = find<TextView>(d_homeTeamYellowCards)
        val sAwayTeamYellowCards = find<TextView>(d_awayTeamYellowCards)
        val sHomeTeamRedCards = find<TextView>(d_homeTeamRedCards)
        val sAwayTeamRedCards = find<TextView>(d_awayTeamRedCards)
        val sAwayTeamLineupSubstitutes = find<TextView>(d_awayTeamLineupSubstitutes)
        val sHomeTeamLineupSubstitutes = find<TextView>(d_homeTeamLineupSubstitutes)

        sMatchDate.text = dataEvents[0].dateOfMatch
        sLeagueName.text = dataEvents[0].leagueName
        sHomeTeamName.text = dataEvents[0].homeTeamName
        sAwayTeamName.text = dataEvents[0].awayTeamName
        sHomeTeamScore.text = dataEvents[0].homeScore
        sAwayTeamScore.text = dataEvents[0].awayScore
        sHomeTeamGoalDetail.text = dataEvents[0].homeTeamGoalDetails
        sAwayTeamGoalDetail.text = dataEvents[0].awayTeamGoalDetails
        sHomeTeamFormation.text = dataEvents[0].homeTeamFormation
        sAwayTeamFormation.text = dataEvents[0].awayTeamFormation
        sHomeTeamLineupForward.text = dataEvents[0].homeTeamLineupForward
        sAwayTeamLineupForward.text = dataEvents[0].awayTeamLineupForward
        sHomeTeamLineupMidfield.text = dataEvents[0].homeTeamLineupMidfield
        sAwayTeamLineupMidfield.text = dataEvents[0].awayTeamLineupMidfield
        sHomeTeamLineupDefense.text = dataEvents[0].homeTeamLineupDefense
        sAwayTeamLineupDefense.text = dataEvents[0].awayTeamLineupDefense
        sHomeTeamLineupGoalkeeper.text = dataEvents[0].homeTeamLineupGoalkeeper
        sAwayTeamLineupGoalkeeper.text = dataEvents[0].awayTeamLineupGoalkeeper
        sHomeTeamLineupSubstitutes.text = dataEvents[0].homeTeamLineupSubstitutes
        sAwayTeamLineupSubstitutes.text = dataEvents[0].awayTeamLineupSubstitutes
        sHomeTeamYellowCards.text = dataEvents[0].homeTeamYellowCards
        sAwayTeamYellowCards.text = dataEvents[0].awayTeamYellowCards
        sHomeTeamRedCards.text = dataEvents[0].homeTeamRedCards
        sAwayTeamRedCards.text = dataEvents[0].awayTeamRedCards

        eventsData = MatchList(dataEvents[0].eventId,
                dataEvents[0].dateOfMatch,
                dataEvents[0].awayTeamId,
                dataEvents[0].homeTeamId,
                dataEvents[0].homeScore,
                dataEvents[0].awayScore,
                dataEvents[0].awayTeamVsHomeTeam,
                dataEvents[0].leagueId,
                dataEvents[0].leagueName,
                dataEvents[0].awayTeamFormation,
                dataEvents[0].awayTeamName,
                dataEvents[0].awayTeamGoalDetails,
                dataEvents[0].awayTeamLineupDefense,
                dataEvents[0].awayTeamLineupForward,
                dataEvents[0].awayTeamLineupGoalkeeper,
                dataEvents[0].awayTeamLineupMidfield,
                dataEvents[0].awayTeamLineupSubstitutes,
                dataEvents[0].awayTeamYellowCards,
                dataEvents[0].awayTeamRedCards,
                dataEvents[0].homeTeamName,
                dataEvents[0].homeTeamGoalDetails,
                dataEvents[0].homeTeamFormation,
                dataEvents[0].homeTeamLineupDefense,
                dataEvents[0].homeTeamLineupForward,
                dataEvents[0].homeTeamLineupGoalkeeper,
                dataEvents[0].homeTeamLineupMidfield,
                dataEvents[0].homeTeamLineupSubstitutes,
                dataEvents[0].homeTeamRedCards,
                dataEvents[0].homeTeamYellowCards
        )

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
